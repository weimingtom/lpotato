package{
import flash.display.Sprite;
import flash.events.Event;
import flash.events.IOErrorEvent;
import flash.events.SecurityErrorEvent;
import flash.events.ProgressEvent;
import flash.external.ExternalInterface;
import flash.net.Socket;
import flash.system.Security;
import flash.utils.ByteArray;

import com.ericfeminella.collections.HashMap;

public class SocketBridge extends Sprite{
    private static var sockets:HashMap = new HashMap();
    
    public static function CAN_I_HAS_SOCKET():Boolean {return true;}
    
    public function SocketBridge() {
        Security.allowDomain("*");
		Security.allowInsecureDomain("*");
		init();// javascript 初始化
    }

    public static function handle_connect(id:String, event:Event):void {
        ExternalInterface.call( "(function(id){ var inst = window.FlashSocket._instances[id]; if (inst.on_connect) inst.on_connect();})", id);
    }
    public static function handle_close(id:String, event:Event):void {
        sockets.remove(id);
        ExternalInterface.call( "(function(id){ var inst = window.FlashSocket._instances[id]; if (inst.on_close) inst.on_close();})", id);
    }
    public static function handle_io_error(id:String, event:Event):void {
        ExternalInterface.call( "(function(id, err){ var inst = window.FlashSocket._instances[id]; if (inst.on_io_error) inst.on_io_error(err);})", id, event.type);
    }
    public static function handle_security_error(id:String, event:Event):void {
        ExternalInterface.call( "(function(id, err){ var inst = window.FlashSocket._instances[id]; if (inst.on_security_error) inst.on_security_error(err);})", id, event.type);
    }
    public static function handle_data(id:String, event:Event):void {
    	handle_log(id, "socket response id:" + id);
        var socket:Socket = sockets.getValue(id);
        if (socket != null)  {
        	var socketResp:Array = readResponse(socket);// 拆包,去掉包头,解决粘包问题.
    	 	for (var i:int = 0;i < socketResp.length;i++){
    	 		var msg:String = socketResp[i];
         		ExternalInterface.call( "(function(id, data){ var inst = window.FlashSocket._instances[id]; if (inst.on_data) inst.on_data(data);})", id, msg);
	         }
         }
    }
    /**
	 * 日志
	 */
    public static function handle_log(id:String, msg:String):void {
    	ExternalInterface.call( "(function(id, data){ var inst = window.FlashSocket._instances[id]; if (inst.on_log) inst.on_log(data);})", id, msg);
    }
    
    public static function connect(instance_id:String, host:String, port:int):void {
        var socket:Socket = sockets.getValue(instance_id);
        if (socket != null)  {
            socket.connect(host, port); 
        } else {
            socket = new Socket(host, port);
            sockets.put(instance_id, socket);
            
            socket.addEventListener( Event.CONNECT, 
                function(e:Event):void { handle_connect(instance_id, e); }
            );
            
            socket.addEventListener( Event.CLOSE, 
                function(e:Event):void { handle_close(instance_id, e); }
            );
            
            socket.addEventListener( IOErrorEvent.IO_ERROR, 
                function(e:Event):void { handle_io_error(instance_id, e); }
            );
            
            socket.addEventListener( SecurityErrorEvent.SECURITY_ERROR, 
                function(e:Event):void { handle_security_error(instance_id, e); }
            );
            
            socket.addEventListener( ProgressEvent.SOCKET_DATA, 
                function(e:Event):void { handle_data(instance_id, e); }
            );
        }
    }
    
    public static function close(instance_id:String):void {
        var socket:Socket = sockets.getValue(instance_id);
        if (socket != null) {
            if (socket.connected) {
                socket.close();
                sockets.remove(instance_id);
            }
        }
    }
  
    /**
	 * 读取响应数据,去掉包头. 由于Flash动画是基于帧的，所以Flash的socket也是受帧影响的，as中所有事件也是随帧触发的。
	 * 比如一个30fps的swf，它每1/30 秒检测一遍事件，也就是ENTER_FRAME是最底层的event，
	 * 一般的键盘持续响应、动画都是基于它来检测的。Socket中的事件也是一样，这就意为着，
	 * 如果再一个1/30秒内，socket收到1条以上的消息，事件却只触发了一次，于是缓冲内的东西就被会被一次取出，
	 * 几条消息就粘连到一起了。其它应用平台也会有粘连情况，只是不像flash这么严重，
	 * 因为它的检测间隔太大，远比什么“cpu时钟级”、“微秒级”高得多。
	 * 
	 * 解决粘包问题. http://www.cnblogs.com/jiahuafu/archive/2009/10/21/1587479.html
	 * http://www.vckbase.com/document/viewdoc/?id=1203
	 */
    public static function readResponse(socket:Socket):Array{
       	var socketRespArr:ByteArray = new ByteArray();
    	var result:Array = [];
    	socket.readBytes(socketRespArr);// 读取字节
    	socketRespArr.position = 0;
//    	trace("read socket");
    	var headResp:ByteArray = null;
    	var bodyLength:int = 16;// 包头大小
    	while(socketRespArr.bytesAvailable > bodyLength){
//    		trace("while begin.socketRespArr bytesAvailable:" + socketRespArr.bytesAvailable + " position:" + socketRespArr.position);
    		headResp = new ByteArray();
    		headResp.writeBytes(socketRespArr, socketRespArr.position, 16);
    		bodyLength = getBodyLength(headResp);
    		socketRespArr.position = socketRespArr.position + 16;
//    		trace("bodyLength:" + bodyLength);
    		if(bodyLength == -1){
    			break;
    		}
    		if(socketRespArr.bytesAvailable >= bodyLength){
//    			trace("从socket resp读取数据, socketRespArr bytesAvailable:" + socketRespArr.bytesAvailable + " position:" + socketRespArr.position);
    			var bodyContent:ByteArray = new ByteArray();
    			bodyContent.writeBytes(socketRespArr, socketRespArr.position, bodyLength);
    			socketRespArr.position = socketRespArr.position + bodyLength;
    			bodyContent.position = 0;
    			result.push(bodyContent.readUTFBytes(bodyContent.bytesAvailable));
    		}
//    		trace("while end======================\r\n");
    	}
    	return result;
    }
    /**
	 * 获取包体长度
	 */
    public static function getBodyLength(head:ByteArray):int{
    	if(head[8] == undefined || head[9] == undefined)
    		return -1;
    	return head[8] | (head[9] << 8);
    }
    
    /**
	 * 组装包头.
	 * 
	 * @param bodyLength
	 *            包体长度
	 * @return {ByteArray} 16个字节长度的包头.
	 */
    public static function header(bodyLength:int):ByteArray{
    	var byteArr:ByteArray = new ByteArray();
       	var orgId:int=1;
       	writeDubleByte(orgId, byteArr);// 送端主机编码 0-1
       	writeDubleByte(orgId, byteArr);// 发送端通信端口号 2-3
       	writeDubleByte(orgId, byteArr);// 接收端主机编码 4-5
       	writeDubleByte(orgId, byteArr);// 接收端通信端口号 6-7
        writeDubleByte(bodyLength, byteArr);// xml包体的长度 8-9
        byteArr.writeByte(0);// 包类型标识：0x00-单独包，0x01-拆分包的中间包，0x02-拆分包的最后一个包 10
        byteArr.writeByte(1);// 拆分包的包序号，从1开始计数，最大只允许255个包 11
        writeDubleByte(orgId, byteArr);// 包序号，用于一个包拆分之前的计数 12-13
        byteArr.writeByte(5);// 编码格式 0-3 gbk,4 pda utf8, 5 client(utf8) 14
        byteArr.writeByte(0);// 保留，保证包头大小为4的整数倍 15
       	return byteArr;
    }
    
	/** 转换byte */
    public static function writeDubleByte(i:int, byteArr:ByteArray):void{
    	byteArr.writeByte(( i >>> 0 ) & 0xFF);
    	byteArr.writeByte(( i >>> 8) & 0xFF);
    }
    /**
	 * 输出字节流.
	 * 
	 * @return {Array<int>}
	 */
    public static function println(instance_id:String, byteArr:ByteArray):Array{
    	//handle_log(id, "Before length: " + byteArr.length +" bytesAvailable: " + byteArr.bytesAvailable + " position: " + byteArr.position);
    	//trace("Before length: " + byteArr.length +" bytesAvailable: " + byteArr.bytesAvailable + " position: " + byteArr.position);
    	var respByte:Array = [];
    	byteArr.position = 0;
    	 for (var i:int = 0;i < byteArr.length;i++){
    		 respByte.push(byteArr.readByte());
    	}
    	byteArr.position = 0;
    	//trace("After length: " + byteArr.length +" bytesAvailable: " + byteArr.bytesAvailable + " position: " + byteArr.position);
    	//trace("print Byte: "+ respByte.join(","));
    	handle_log(instance_id, "print Byte: "+ respByte.join(","));
    	return respByte;
    }
    
    /** 发送数据 */
    public static function write(instance_id:String, msg:String):void {
        var socket:Socket = sockets.getValue(instance_id);
        if (socket != null) {
            if (socket.connected) {
            	var bodyArr:ByteArray = new ByteArray();
            	bodyArr.writeUTFBytes(msg);
            	// println(byte);
            	
            	var headerArr:ByteArray = header(bodyArr.length);// header
            	//println(instance_id, headerArr);
            	var byteArr:ByteArray = new ByteArray();
            	byteArr.writeBytes(headerArr, 0, headerArr.length);// 添加包头
            	byteArr.writeBytes(bodyArr, 0, bodyArr.length);
            	
            	handle_log(instance_id, "socket package body Length:" + bodyArr.length);
                socket.writeBytes(byteArr,0,byteArr.length);
                socket.flush();
            }
        }
    }
    
    public static function loadPolicyFile(path:String):void {
      Security.loadPolicyFile(path);
    }
    
    public static function init():void {
        ExternalInterface.addCallback("loadPolicyFile", loadPolicyFile);    
        ExternalInterface.addCallback("connect", connect);    
        ExternalInterface.addCallback("close", close);    
        ExternalInterface.addCallback("write", write);
        ExternalInterface.addCallback("CAN_I_HAS_SOCKET", CAN_I_HAS_SOCKET);
        ExternalInterface.call([
        "(function(){",
            "if (window.FlashSocket) return;",
            "var Class = function(properties){",
                "var klass = function(event_handlers){ ",
                    "for (var p in event_handlers) {",
                        "if (event_handlers.hasOwnProperty(p)) {",
                            "this[p] = event_handlers[p];",
                        "}",
                    "}",
                    "return this.init.apply(this);",
                "};",
                "klass.prototype = properties;",
                "klass.constructor = arguments.callee;",
                "return klass;",
            "};",
            "window.FlashSocket = new Class({",
                "init: function(){",
                    "this._instance = ''+window.FlashSocket._instances.length;",
                    "window.FlashSocket._instances.push(this);",
                "},",
                "close: function(){ ",
                    "window.FlashSocket._instances[this._instance] = null;",
                    "window.FlashSocket._bridge.close(this._instance );",
                "},",
                "write: function(data){ ",
                    "window.FlashSocket._bridge.write(this._instance, data);",
                "},",
                "connect: function(host, port) {",
                    "window.FlashSocket._bridge.connect(this._instance, host, port);",
                "},",
                "loadPolicyFile: function(path) {",
                    "window.FlashSocket._bridge.loadPolicyFile(path);",
                "}",
            "});",
            "window.FlashSocket._instances = [];",
            "var f = function(tag){",
                "var elems = document.getElementsByTagName(tag);",
                "for (var i=0; i<elems.length; i++) if (elems[i].CAN_I_HAS_SOCKET) return elems[i];",
            "};",
            "window.FlashSocket._bridge = f('embed') || f('object');",
        "})" ].join('') );
    }
}
}
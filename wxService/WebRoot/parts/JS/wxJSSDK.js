var wxJSSDK ={
		version : "1.0",     //版本号
		appName:"",		//使用当前库的开发者，可以配置应用的名字
		isReady:false,		//微信JSSDK是否
		access_token:"",		//令牌
		ticket:"",  //微信临时票据
		config:{  
			debug:true,  //开启调试模式，调用的所有api的返回值会在客户端alert出来，若要看传入的参数，可在PC端查看
			appId:"wx7a32eddd346db919",   //华电网络appId
			timestamp:Math.ceil(new Date().getTime()/1000).toString(),//生成签名时间戳
			nonceStr:'ddwx_wxJSSDK', //生成签名随机串
			signature:"", //签名
			jsApiList :[
			            "onMouseShareTimeline"  //分享到朋友圈
			            ]  //需要使用的JSSDK几口列表；
		},
		init:function(){   //初始化函数方法
			if(!wx){
				alert("微信接口调用失败，请检查是否引入微信JS！");
				return;
			}
			var that = this; //保存当前作用域，方便函数返回调用；
			
			//获取令牌
			this.wx_get_token(function(data){  
				if(data.access_token){
					Cookie.Set("access_token",data.access_token_3600);
					that.access_token=data.access_token;
				}
			});
			
			//获取票据
			
			//获取签名
		},
		
		//获取令牌方法；
		wx_get_token:function(call){
			this.access_token = Cookie.Get("access_token");
		}
		
}



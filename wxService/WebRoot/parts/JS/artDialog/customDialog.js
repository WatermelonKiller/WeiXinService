(function($) {
	$.imDialog = ( {
		alert : function(content,callback,width,height,icon) {
			var throughBox = $.dialog.through;
			var _width = '260px';
			var _height = '180px';
			if(width)_width=width+'px';
			if(height)_height=height+'px';
			return throughBox({
				id:'Alert',
				content : content,
				lock : true,
				icon : icon,
				fixed : true,
				ok : callback?callback:true,
				okVal:'OK',
				width:_width,
				height:_height
			});
		},
		open : function(url,title,width,height,id) {
			
			var scrHeight = window.screen.height;
			var scrWidth  = window.screen.width;
			
			var _width = '800px';
			var _height = '500px';
			
			if(scrWidth > 1024 && scrHeight > 768){
				_width = '800px';
				_height = '600px';
			}
    		
			if(width)_width=width+'px';
			if(height)_height=height+'px';
			_id = id||'Open';
			return $.dialog.open(url, {
				id:_id,
				title : title?title:'提示',
				lock : true,
				width:_width,
				height:_height
			});
		},
		open_closeFun : function(url,title,width,height,id,closeFun) {
			var scrHeight = window.screen.height;
			var scrWidth  = window.screen.width;
			
			var _width = '800px';
			var _height = '500px';
			
			if(scrWidth > 1024 && scrHeight > 768){
				_width = '800px';
				_height = '600px';
			}
    		
			if(width)_width=width+'px';
			if(height)_height=height+'px';
			_id = id||'Open';
			return $.dialog.open(url, {
				id:_id,
				title : title?title:'提示',
				lock : true,
    			close: function(){
    				if(closeFun)
    					$.dialog.open.origin.closeCallBack();
    			},
				width:_width,
				height:_height
			});
		},
		confirm : function(content,yes,no,width,height){
			var throughBox = $.dialog.through;
			var _width = '260px';
			var _height = '180px';
			if(width)_width=width+'px';
			if(height)_height=height+'px';
			return throughBox({
				id: 'Confirm',
				icon: 'question',
				fixed: true,
				lock: true,
				content: content,
				width: _width,
				height: _height,
				ok: function (here) {
					return yes.call(this, here);
				},
				cancel: function (here) {
					return no && no.call(this, here);
				}
			});
		},
		close:function(){
			$.dialog.close();
		},
		tips : function (content, time) {
		    /*return artDialog({
		        id: 'Tips',
		        title: false,
		        cancel: false,
		        fixed: true,
		        lock: true
		    }).content(content).time(time || 1.5);*/
			var throughBox = $.dialog.through;//锁住最外层窗口
			var _width = '260px';
			var _height = '180px';
			return throughBox( {
				id:'Tips',
				title:false,
				esc:false,
				content : content,
				lock : true,
				fixed : true,
				width:_width,
				height:_height
			});
		},
		
		openner : function(){
			return $.dialog.open.origin;
		}
		
	});

})(jQuery);
(function($) {
	$.ipage = function(fx){
		this.options={};
		$.extend(this.options, this.defaultOptions); 
		$.extend(this.options, fx); 
		this.init();
	};
	$.ipage.prototype={
		defaultOptions:{
			'autoTotalWidth':true,
			'intervalClass':true,
			'overChangeClass':false,
			'ajax':false,
			'debug':false,
			'sizable':false,
			'jump':true,
			'display':5,
			'pSize':10,
			'pTotaSize':0,
			'pTotaPage':1,
			'pCurrPage':1,
			'iId':'',
			'ilinkId':'',
			'action':'',
			'baseName':'',
			'sizegroup':[20,50,100,200]
		},
		init:function(){
			this.debug('Class ipage Init');
			this.link = $('#'+this.options.ilinkId);
			if(this.link.length<1){
				this.error('page link contain id \'ilinkId\' not be found');
				return;
			}
			if(this.options.pTotaSize>0){
				this.page = $('#'+this.options.iId);
				if(this.page.length==1){
					this.autoWidth();
					this.autoInterval();
					this.autoOverChange();
				}
				this.createLinkBar();
				this.bindLinkBar();
			}
			this.debug('Class ipage Inited');
		},
		createLinkBar: function(){
			this.debug('Class ipage LinkBar Init');
			var link = this.link;
			this.showPageData = this.createSpan2('','共',this.options.pTotaSize,'条数据，共',this.options.pTotaPage,'页。当前是第');
			link.append(this.showPageData);
			if(this.options.sizable){
				this.debug('size change enable');
				this.sizeSelect = this.createSelect('page_i');
				link.append(this.sizeSelect);
				var sizegroup = this.options.pTotaPage;
				var len;
				if(sizegroup!=null&&(len=sizegroup)>0){
					for(var i=1;i<=len;i++){
						this.sizeSelect.append(this.createOption(i,i,i==this.options.pCurrPage));
					}
				}
				link.append(this.createSpan('','页'));
			}
			
			
			if(this.options.jump){
				this.debug('jump page enable');
				this.curInp = this.createCurInp('page_n');
				link.append(this.curInp);
				this.curBtn = this.createCurBtn('page_btn','GO');
				link.append(this.curBtn);
			}
			
			
			
			
			link.append(this.createSpan('','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'));
			link.append(this.createSpan('','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'));
			link.append(this.createSpan('','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'));
			link.append(this.createSpan('','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'));
			link.append(this.createSpan('','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'));
			this.debug('Class ipage LinkBar Inited');
			
			if(this.options.pCurrPage != 1){
				this.first = this.createImg('首页','first');
				link.append(this.first);
				this.prev = this.createImg('上一页','previous'); 
				link.append(this.prev);
			}

			
			
			if(this.options.pTotaPage<this.options.display){
				this.prevStart = 1;
				this.nextEnd = this.options.pTotaPage;
			}else{
				this.prevStart = Math.min(this.options.pTotaPage-this.options.display+1,
					Math.max(1,this.options.pCurrPage-
					(this.options.display-this.options.display%2)/2+
					(this.options.display%2==0?1:0)));
					
				this.nextEnd = Math.max(this.options.display,Math.min(this.options.pTotaPage,this.options.pCurrPage+
					(this.options.display-this.options.display%2)/2));
			}
			this.debug('Class ipage LinkBar prevStart:'+this.prevStart);
			this.debug('Class ipage LinkBar nextEnd:'+this.nextEnd);
			
			this.prevArray = [];
			var alen = this.options.pCurrPage-this.prevStart;
			for(var i=0;i<alen;i++){
				this.prevArray.push(this.createLink('',this.prevStart+i));
				this.prevArray[i].data('no',this.prevStart+i);
				//link.append(this.prevArray[i]);
			}
			
			//this.current = this.createLink('current',this.options.pCurrPage);
			//link.append(this.current);
			
			this.nextArray = [];
			alen = this.nextEnd-this.options.pCurrPage;
			for(var i=0;i<alen;i++){
				this.nextArray.push(this.createLink('',this.options.pCurrPage+i+1));
				this.nextArray[i].data('no',this.options.pCurrPage+i+1);
				//link.append(this.nextArray[i]);
			}
			
			if(this.options.pCurrPage != this.options.pTotaPage){
			this.next = this.createImg('下一页 ','next');
			link.append(this.next);
			this.last = this.createImg('尾页 ','end');
			link.append(this.last);
			}
		},
		bindLinkBar: function(){
			this.debug('Class ipage LinkBar Bind');
			var oThis = this;
			if(this.options.pCurrPage>1){
				this.first.click(function(event){oThis.jump(1,oThis.options.pSize);});
				this.prev.click(function(event){oThis.jump(oThis.options.pCurrPage-1,oThis.options.pSize);});
			}
			
			var alen=this.prevArray.length;
			for(var i=0;i<alen;i++){
				this.prevArray[i].click(function(event){oThis.jump($(this).data('no'),oThis.options.pSize);});
			}
			alen=this.nextArray.length;
			for(var i=0;i<alen;i++){
				this.nextArray[i].click(function(event){oThis.jump($(this).data('no'),oThis.options.pSize);});
			}
			
			if(this.options.pCurrPage<this.options.pTotaPage){
				this.next.click(function(event){oThis.jump(oThis.options.pCurrPage+1,oThis.options.pSize);});
				this.last.click(function(event){oThis.jump(oThis.options.pTotaPage,oThis.options.pSize);});
			}
			if(this.options.jump){
				this.curInp.keypress(function(event){return oThis.numLimitOnKeypress(event,oThis);});
				this.curBtn.click(function(event){return oThis.jumpOnClick(event,oThis);});
			}
			if(this.options.sizable){
				this.sizeSelect.change(function(event){oThis.sizeOnChange(event,oThis);});
			}
			this.debug('Class ipage LinkBar Binded');
		},
		numLimitOnKeypress: function(event,oThis){
			oThis.debug('jump page input press key:'+event.keyCode);
			return !(event.keyCode < 45 || event.keyCode > 57);
        },
        jumpOnClick:function(event,oThis){
        	if(!oThis.options.jump)return false;
        	var no = oThis.curInp.val()*1;
        	if(no>oThis.options.pTotaPage||no<1){
        		alert('页数应在1-'+oThis.options.pTotaPage+'之间');
        	}else{
        		oThis.jump(no,oThis.options.pSize);
        	};
        	return false;
        },
        sizeOnChange:function(event,oThis){
        	if(!oThis.options.sizable)return false;
        	var size = oThis.sizeSelect.val()*1;
    		oThis.jump(size,oThis.options.pSize);
        	return false;
        },
        autoWidth : function(){
        	if(this.options.autoTotalWidth){
				var twidth = 0;
				this.page.find('.ipagetitle li div').each(function(){
					twidth = 0;
				});
				this.page.css('width',twidth+1);
				this.debug('Class ipage autoTotalSize:'+twidth);
			}
        },
        autoInterval : function(){
        	if(this.options.intervalClass){
				this.page.find('.ipagelist li:even').addClass('normal');
				this.page.find('.ipagelist li:odd').addClass('add');
				this.debug('Class ipage autoInterval');
			}
        },
        autoOverChange : function(){
        	if(this.options.overChangeClass){
				$('.ipagelist li').hover(function() {
					$(this).addClass("hover");
				}, function() {
					$(this).removeClass("hover");
				});
				this.debug('Class ipage autoOverChange');
			}
        },
        jump: function(no,size){
        	this.debug('jump page(ajax='+this.options.ajax+'):'+no+','+size);
        	var url = this.options.action+this.options.baseName+'currentPage='+no;
        	if(this.options.sizable){
        		url += '&'+this.options.baseName+'showCount='+size;
        	}
        	if(this.options.ajax){
        		
        	}else{
    			location.href=url;
    		}
        },
        createLink: function(cssclass,text){
        	return $('<a class=\''+cssclass+'\'>'+text+'</a>');
        },
        createCurInp: function(cssclass){
        	return $('<input class=\''+cssclass+'\' type=\'text\' value=\''+this.options.pCurrPage+'\'/>');
        },
        createCurBtn: function(cssclass,text){
        	return $('<button class=\''+cssclass+'\'>'+text+'</button>');
        },
        createSpan: function(cssclass,text){
        	return $('<span class=\''+cssclass+'\'>'+text+'</span>');
        },
        createSpan2: function(cssclass,text1,text2,text3,text4,text5){
        	return $('<span class=\''+cssclass+'\'>'+text1+'<font color="#849ed6">'+text2+'</font>'+text3+'<font color="#849ed6">'+text4+'</font>'+text5+'</span>');
        },
        createSelect: function(cssclass){
        	return $('<select class=\''+cssclass+'\'></select>');
        },
        createOption: function(val,text,selected){
        	return $('<option value=\''+val+'\' '+(selected ? 'selected':'')+'>'+text+'</option>');
        },
        createImg:function(text,text1){
        	return $('<img name="'+text1+'" title="'+text+'" style="cursor: hand;margin-right:8px;" src="/hz_oa/resources/js/ipage/im	ages/'+text1+'.jpg" complete="complete"/>');
        },
        setDebug: function(idDebug){
        	this.options.debug=!!idDebug;
        },
        debug: function(str){
        	if(this.options.debug&&console!=null)
				console.info(str);
        },
        warn: function(str){
        	if(console!=null)
				console.warn(str);
        },
        error: function(str){
        	if(console!=null)
				console.error(str);
        }
	};
})(jQuery);
$(document).ready(function(){
	var pageJson = $('ilink').attr('value');
	if(pageJson != undefined){
		var pageObj = JSON.parse(pageJson);
		function initPage(){
			new $.ipage(pageObj);
		}
		setTimeout(initPage,0);
	}
});
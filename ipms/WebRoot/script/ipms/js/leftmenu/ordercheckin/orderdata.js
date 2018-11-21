


//双击跳转订单详情页
function checkorderinfo(e) {
			var orderid = $(e.children[0]).html();
			window.parent.parent.JDialog.openWithNoTitle("", path + "/page/ipmshotel/order/order_details.jsp?orderid=" + orderid,1179,733);
		}
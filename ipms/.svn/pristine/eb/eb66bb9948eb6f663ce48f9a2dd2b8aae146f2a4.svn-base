
jQuery(document).ready(function() {
});

var LODOP; //声明为全局变量 
function prn1_preview() {
	CreateOneFormPage();	
	LODOP.PREVIEW();	
};

function CreateOneFormPage() {
	LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));  
	LODOP.PRINT_INIT("print");                                                     //进程名
	var strBodyStyle="<style>table,td { border: 1 solid #000000;border-collapse:collapse }</style>";
	var strFormHtml=strBodyStyle+"<body>"+jQuery("#grid_content").html()+"</body>";//设置表格边框
	LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_表单一");
	LODOP.SET_PRINT_STYLE("FontSize",1);
	LODOP.SET_PRINT_STYLE("Bold",1);
	LODOP.SET_PRINT_MODE("PRINT_PAGE_PERCENT","Auto-Width");//按整宽且不变形，即按整宽的同时，高度与之同比
	LODOP.ADD_PRINT_HTM(0,0,0,600,strFormHtml);
};
	
function printRecords() {
	LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));  
	LODOP.PRINT_INIT("print");                                                     //进程名
	var strBodyStyle="<style>table,td { border: 1 solid #000000;border-collapse:collapse }</style>";
	var strFormHtml=strBodyStyle+"<body>"+jQuery("#grid_content").html()+"</body>";//设置表格边框
	LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_表单一");
	LODOP.SET_PRINT_STYLE("FontSize",1);
	LODOP.SET_PRINT_STYLE("Bold",1);
	LODOP.SET_PRINT_MODE("PRINT_PAGE_PERCENT","Auto-Width");//按整宽且不变形，即按整宽的同时，高度与之同比
	LODOP.ADD_PRINT_HTM(0,0,0,600,strFormHtml);	var LODOP=getLodop(document.getElementById('LODOP_OB'),document.getElementById('LODOP_EM'));
	LODOP.PRINT();
		
	//设定纸张大小 参数 横/纵, 宽, 高, 页名(可空)
	//LODOP.SET_PRINT_PAGESIZE(intOrient, intPageWidth, intPageHeight, strPageName);
	//LODOP.ADD_PRINT_RECT(70, 27, 634, 242, 0, 1);
	//LODOP.ADD_PRINT_TEXT(29, 236, 279, 38, "");
	//LODOP.SET_PRINT_STYLEA(2, "FontSize", 14);
	//LODOP.SET_PRINT_STYLEA(2, "Bold", 1);
	//打印文本
	//LODOP.ADD_PRINT_HTM(88, 40, 321, 185, jQuery("#grid_content").html());
}



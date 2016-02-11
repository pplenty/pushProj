<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<!DOCTYPE html>
<html>
<head>
<title>Test</title>
<script src="//code.jquery.com/jquery-1.12.0.min.js">
<script src="//cdn.ckeditor.com/4.5.7/basic/ckeditor.js"></script>
<link href="./test.css" rel="stylesheet">
</head>
<body>
	<div id="cke_pushPopupHtmlEditor"
		class="cke_1 cke cke_reset cke_chrome cke_editor_pushPopupHtmlEditor cke_ltr cke_browser_webkit"
		dir="ltr" lang="en" role="application"
		aria-labelledby="cke_pushPopupHtmlEditor_arialbl"
		style="width: 461px;">
		<span id="cke_pushPopupHtmlEditor_arialbl" class="cke_voice_label">Rich
			Text Editor, pushPopupHtmlEditor</span>
		<div class="cke_inner cke_reset" role="presentation">
			<span id="cke_1_top" class="cke_top cke_reset_all"
				role="presentation" style="height: auto; -webkit-user-select: none;"><span
				id="cke_14" class="cke_voice_label">Editor toolbars</span><span
				id="cke_1_toolbox" class="cke_toolbox" role="group"
				aria-labelledby="cke_14" onmousedown="return false;"><span
					id="cke_17" class="cke_toolbar" role="toolbar"><span
						class="cke_toolbar_start"></span><span class="cke_toolgroup"
						role="presentation"><a id="cke_18"
							class="cke_button cke_button__source cke_button_off"
							href="javascript:void('Source')" title="Source" tabindex="-1"
							hidefocus="true" role="button" aria-labelledby="cke_18_label"
							aria-haspopup="false"
							onkeydown="return CKEDITOR.tools.callFunction(2,event);"
							onfocus="return CKEDITOR.tools.callFunction(3,event);"
							onclick="CKEDITOR.tools.callFunction(4,this);return false;"><span
								class="cke_button_icon cke_button__source_icon"
								style="background-image: url(https://send.pushpia.com/resources/js/ckeditor4/plugins/icons.png?t=F969); background-position: 0 -840px; background-size: auto;">&nbsp;</span><span
								id="cke_18_label"
								class="cke_button_label cke_button__source_label"
								aria-hidden="false">Source</span></a></span><span class="cke_toolbar_end"></span></span><span
					id="cke_19" class="cke_toolbar" role="toolbar"><span
						class="cke_toolbar_start"></span><span class="cke_toolgroup"
						role="presentation"><a id="cke_20"
							class="cke_button cke_button__bold cke_button_off"
							href="javascript:void('Bold')" title="Bold" tabindex="-1"
							hidefocus="true" role="button" aria-labelledby="cke_20_label"
							aria-haspopup="false"
							onkeydown="return CKEDITOR.tools.callFunction(5,event);"
							onfocus="return CKEDITOR.tools.callFunction(6,event);"
							onclick="CKEDITOR.tools.callFunction(7,this);return false;"><span
								class="cke_button_icon cke_button__bold_icon"
								style="background-image: url(https://send.pushpia.com/resources/js/ckeditor4/plugins/icons.png?t=F969); background-position: 0 -24px; background-size: auto;">&nbsp;</span><span
								id="cke_20_label"
								class="cke_button_label cke_button__bold_label"
								aria-hidden="false">Bold</span></a><a id="cke_21"
							class="cke_button cke_button__italic cke_button_off"
							href="javascript:void('Italic')" title="Italic" tabindex="-1"
							hidefocus="true" role="button" aria-labelledby="cke_21_label"
							aria-haspopup="false"
							onkeydown="return CKEDITOR.tools.callFunction(8,event);"
							onfocus="return CKEDITOR.tools.callFunction(9,event);"
							onclick="CKEDITOR.tools.callFunction(10,this);return false;"><span
								class="cke_button_icon cke_button__italic_icon"
								style="background-image: url(https://send.pushpia.com/resources/js/ckeditor4/plugins/icons.png?t=F969); background-position: 0 -48px; background-size: auto;">&nbsp;</span><span
								id="cke_21_label"
								class="cke_button_label cke_button__italic_label"
								aria-hidden="false">Italic</span></a><a id="cke_22"
							class="cke_button cke_button__underline cke_button_off"
							href="javascript:void('Underline')" title="Underline"
							tabindex="-1" hidefocus="true" role="button"
							aria-labelledby="cke_22_label" aria-haspopup="false"
							onkeydown="return CKEDITOR.tools.callFunction(11,event);"
							onfocus="return CKEDITOR.tools.callFunction(12,event);"
							onclick="CKEDITOR.tools.callFunction(13,this);return false;"><span
								class="cke_button_icon cke_button__underline_icon"
								style="background-image: url(https://send.pushpia.com/resources/js/ckeditor4/plugins/icons.png?t=F969); background-position: 0 -144px; background-size: auto;">&nbsp;</span><span
								id="cke_22_label"
								class="cke_button_label cke_button__underline_label"
								aria-hidden="false">Underline</span></a><a id="cke_23"
							class="cke_button cke_button__strike cke_button_off"
							href="javascript:void('Strikethrough')" title="Strikethrough"
							tabindex="-1" hidefocus="true" role="button"
							aria-labelledby="cke_23_label" aria-haspopup="false"
							onkeydown="return CKEDITOR.tools.callFunction(14,event);"
							onfocus="return CKEDITOR.tools.callFunction(15,event);"
							onclick="CKEDITOR.tools.callFunction(16,this);return false;"><span
								class="cke_button_icon cke_button__strike_icon"
								style="background-image: url(https://send.pushpia.com/resources/js/ckeditor4/plugins/icons.png?t=F969); background-position: 0 -72px; background-size: auto;">&nbsp;</span><span
								id="cke_23_label"
								class="cke_button_label cke_button__strike_label"
								aria-hidden="false">Strikethrough</span></a></span><span
						class="cke_toolbar_end"></span></span><span id="cke_24"
					class="cke_toolbar" role="toolbar"><span
						class="cke_toolbar_start"></span><span class="cke_toolgroup"
						role="presentation"><a id="cke_25"
							class="cke_button cke_button__justifyleft cke_button_off"
							href="javascript:void('Align Left')" title="Align Left"
							tabindex="-1" hidefocus="true" role="button"
							aria-labelledby="cke_25_label" aria-haspopup="false"
							onkeydown="return CKEDITOR.tools.callFunction(17,event);"
							onfocus="return CKEDITOR.tools.callFunction(18,event);"
							onclick="CKEDITOR.tools.callFunction(19,this);return false;"><span
								class="cke_button_icon cke_button__justifyleft_icon"
								style="background-image: url(https://send.pushpia.com/resources/js/ckeditor4/plugins/justify/icons/justifyleft.png?t=F969); background-position: 0 0px; background-size: 16px;">&nbsp;</span><span
								id="cke_25_label"
								class="cke_button_label cke_button__justifyleft_label"
								aria-hidden="false">Align Left</span></a><a id="cke_26"
							class="cke_button cke_button__justifycenter cke_button_off"
							href="javascript:void('Center')" title="Center" tabindex="-1"
							hidefocus="true" role="button" aria-labelledby="cke_26_label"
							aria-haspopup="false"
							onkeydown="return CKEDITOR.tools.callFunction(20,event);"
							onfocus="return CKEDITOR.tools.callFunction(21,event);"
							onclick="CKEDITOR.tools.callFunction(22,this);return false;"><span
								class="cke_button_icon cke_button__justifycenter_icon"
								style="background-image: url(https://send.pushpia.com/resources/js/ckeditor4/plugins/justify/icons/justifycenter.png?t=F969); background-position: 0 0px; background-size: 16px;">&nbsp;</span><span
								id="cke_26_label"
								class="cke_button_label cke_button__justifycenter_label"
								aria-hidden="false">Center</span></a><a id="cke_27"
							class="cke_button cke_button__justifyright cke_button_off"
							href="javascript:void('Align Right')" title="Align Right"
							tabindex="-1" hidefocus="true" role="button"
							aria-labelledby="cke_27_label" aria-haspopup="false"
							onkeydown="return CKEDITOR.tools.callFunction(23,event);"
							onfocus="return CKEDITOR.tools.callFunction(24,event);"
							onclick="CKEDITOR.tools.callFunction(25,this);return false;"><span
								class="cke_button_icon cke_button__justifyright_icon"
								style="background-image: url(https://send.pushpia.com/resources/js/ckeditor4/plugins/justify/icons/justifyright.png?t=F969); background-position: 0 0px; background-size: 16px;">&nbsp;</span><span
								id="cke_27_label"
								class="cke_button_label cke_button__justifyright_label"
								aria-hidden="false">Align Right</span></a><a id="cke_28"
							class="cke_button cke_button__justifyblock cke_button_off"
							href="javascript:void('Justify')" title="Justify" tabindex="-1"
							hidefocus="true" role="button" aria-labelledby="cke_28_label"
							aria-haspopup="false"
							onkeydown="return CKEDITOR.tools.callFunction(26,event);"
							onfocus="return CKEDITOR.tools.callFunction(27,event);"
							onclick="CKEDITOR.tools.callFunction(28,this);return false;"><span
								class="cke_button_icon cke_button__justifyblock_icon"
								style="background-image: url(https://send.pushpia.com/resources/js/ckeditor4/plugins/justify/icons/justifyblock.png?t=F969); background-position: 0 0px; background-size: 16px;">&nbsp;</span><span
								id="cke_28_label"
								class="cke_button_label cke_button__justifyblock_label"
								aria-hidden="false">Justify</span></a></span><span class="cke_toolbar_end"></span></span><span
					id="cke_29" class="cke_toolbar" role="toolbar"><span
						class="cke_toolbar_start"></span><span class="cke_toolgroup"
						role="presentation"><a id="cke_30"
							class="cke_button cke_button__link cke_button_off"
							href="javascript:void('Link')" title="Link" tabindex="-1"
							hidefocus="true" role="button" aria-labelledby="cke_30_label"
							aria-haspopup="false"
							onkeydown="return CKEDITOR.tools.callFunction(29,event);"
							onfocus="return CKEDITOR.tools.callFunction(30,event);"
							onclick="CKEDITOR.tools.callFunction(31,this);return false;"><span
								class="cke_button_icon cke_button__link_icon"
								style="background-image: url(https://send.pushpia.com/resources/js/ckeditor4/plugins/icons.png?t=F969); background-position: 0 -528px; background-size: auto;">&nbsp;</span><span
								id="cke_30_label"
								class="cke_button_label cke_button__link_label"
								aria-hidden="false">Link</span></a><a id="cke_31"
							class="cke_button cke_button__unlink cke_button_disabled "
							href="javascript:void('Unlink')" title="Unlink" tabindex="-1"
							hidefocus="true" role="button" aria-labelledby="cke_31_label"
							aria-haspopup="false" aria-disabled="true"
							onkeydown="return CKEDITOR.tools.callFunction(32,event);"
							onfocus="return CKEDITOR.tools.callFunction(33,event);"
							onclick="CKEDITOR.tools.callFunction(34,this);return false;"><span
								class="cke_button_icon cke_button__unlink_icon"
								style="background-image: url(https://send.pushpia.com/resources/js/ckeditor4/plugins/icons.png?t=F969); background-position: 0 -552px; background-size: auto;">&nbsp;</span><span
								id="cke_31_label"
								class="cke_button_label cke_button__unlink_label"
								aria-hidden="false">Unlink</span></a></span><span class="cke_toolbar_end"></span></span><span
					id="cke_32" class="cke_toolbar" role="toolbar"><span
						class="cke_toolbar_start"></span><span id="cke_15"
						class="cke_combo cke_combo__font cke_combo_off"
						role="presentation"><span id="cke_15_label"
							class="cke_combo_label">Font</span><a class="cke_combo_button"
							title="Font Name" tabindex="-1"
							href="javascript:void('Font Name')" hidefocus="true"
							role="button" aria-labelledby="cke_15_label" aria-haspopup="true"
							onkeydown="return CKEDITOR.tools.callFunction(36,event,this);"
							onfocus="return CKEDITOR.tools.callFunction(37,event);"
							onclick="CKEDITOR.tools.callFunction(35,this);return false;"><span
								id="cke_15_text" class="cke_combo_text cke_combo_inlinelabel">Font</span><span
								class="cke_combo_open"><span class="cke_combo_arrow"></span></span></a></span><span
						id="cke_16" class="cke_combo cke_combo__fontsize cke_combo_off"
						role="presentation"><span id="cke_16_label"
							class="cke_combo_label">Size</span><a class="cke_combo_button"
							title="Font Size" tabindex="-1"
							href="javascript:void('Font Size')" hidefocus="true"
							role="button" aria-labelledby="cke_16_label" aria-haspopup="true"
							onkeydown="return CKEDITOR.tools.callFunction(39,event,this);"
							onfocus="return CKEDITOR.tools.callFunction(40,event);"
							onclick="CKEDITOR.tools.callFunction(38,this);return false;"><span
								id="cke_16_text" class="cke_combo_text cke_combo_inlinelabel">Size</span><span
								class="cke_combo_open"><span class="cke_combo_arrow"></span></span></a></span><span
						class="cke_toolbar_end"></span></span><span id="cke_33"
					class="cke_toolbar" role="toolbar"><span
						class="cke_toolbar_start"></span><span class="cke_toolgroup"
						role="presentation"><a id="cke_34"
							class="cke_button cke_button__textcolor cke_button_off "
							href="javascript:void('Text Color')" title="Text Color"
							tabindex="-1" hidefocus="true" role="button"
							aria-labelledby="cke_34_label" aria-haspopup="true"
							onkeydown="return CKEDITOR.tools.callFunction(41,event);"
							onfocus="return CKEDITOR.tools.callFunction(42,event);"
							onclick="CKEDITOR.tools.callFunction(43,this);return false;"><span
								class="cke_button_icon cke_button__textcolor_icon"
								style="background-image: url(https://send.pushpia.com/resources/js/ckeditor4/plugins/colorbutton/icons/textcolor.png?t=F969); background-position: 0 0px; background-size: 16px;">&nbsp;</span><span
								id="cke_34_label"
								class="cke_button_label cke_button__textcolor_label"
								aria-hidden="false">Text Color</span><span
								class="cke_button_arrow"></span></a><a id="cke_35"
							class="cke_button cke_button__bgcolor cke_button_off "
							href="javascript:void('Background Color')"
							title="Background Color" tabindex="-1" hidefocus="true"
							role="button" aria-labelledby="cke_35_label" aria-haspopup="true"
							onkeydown="return CKEDITOR.tools.callFunction(44,event);"
							onfocus="return CKEDITOR.tools.callFunction(45,event);"
							onclick="CKEDITOR.tools.callFunction(46,this);return false;"><span
								class="cke_button_icon cke_button__bgcolor_icon"
								style="background-image: url(https://send.pushpia.com/resources/js/ckeditor4/plugins/colorbutton/icons/bgcolor.png?t=F969); background-position: 0 0px; background-size: 16px;">&nbsp;</span><span
								id="cke_35_label"
								class="cke_button_label cke_button__bgcolor_label"
								aria-hidden="false">Background Color</span><span
								class="cke_button_arrow"></span></a></span><span class="cke_toolbar_end"></span></span><span
					id="cke_36" class="cke_toolbar" role="toolbar"><span
						class="cke_toolbar_start"></span><span class="cke_toolgroup"
						role="presentation"><a id="cke_37"
							class="cke_button cke_button__image cke_button_off"
							href="javascript:void('Image')" title="Image" tabindex="-1"
							hidefocus="true" role="button" aria-labelledby="cke_37_label"
							aria-haspopup="false"
							onkeydown="return CKEDITOR.tools.callFunction(47,event);"
							onfocus="return CKEDITOR.tools.callFunction(48,event);"
							onclick="CKEDITOR.tools.callFunction(49,this);return false;"><span
								class="cke_button_icon cke_button__image_icon"
								style="background-image: url(https://send.pushpia.com/resources/js/ckeditor4/plugins/icons.png?t=F969); background-position: 0 -360px; background-size: auto;">&nbsp;</span><span
								id="cke_37_label"
								class="cke_button_label cke_button__image_label"
								aria-hidden="false">Image</span></a><a id="cke_38"
							class="cke_button cke_button__table cke_button_off"
							href="javascript:void('Table')" title="Table" tabindex="-1"
							hidefocus="true" role="button" aria-labelledby="cke_38_label"
							aria-haspopup="false"
							onkeydown="return CKEDITOR.tools.callFunction(50,event);"
							onfocus="return CKEDITOR.tools.callFunction(51,event);"
							onclick="CKEDITOR.tools.callFunction(52,this);return false;"><span
								class="cke_button_icon cke_button__table_icon"
								style="background-image: url(https://send.pushpia.com/resources/js/ckeditor4/plugins/icons.png?t=F969); background-position: 0 -912px; background-size: auto;">&nbsp;</span><span
								id="cke_38_label"
								class="cke_button_label cke_button__table_label"
								aria-hidden="false">Table</span></a></span><span class="cke_toolbar_end"></span></span><span
					id="cke_39" class="cke_toolbar" role="toolbar"><span
						class="cke_toolbar_start"></span><span class="cke_toolgroup"
						role="presentation"><a id="cke_40"
							class="cke_button cke_button__preview cke_button_off"
							href="javascript:void('Preview')" title="Preview" tabindex="-1"
							hidefocus="true" role="button" aria-labelledby="cke_40_label"
							aria-haspopup="false"
							onkeydown="return CKEDITOR.tools.callFunction(53,event);"
							onfocus="return CKEDITOR.tools.callFunction(54,event);"
							onclick="CKEDITOR.tools.callFunction(55,this);return false;"><span
								class="cke_button_icon cke_button__preview_icon"
								style="background-image: url(https://send.pushpia.com/resources/js/ckeditor4/plugins/preview/icons/preview.png?t=F969); background-position: 0 0px; background-size: 16px;">&nbsp;</span><span
								id="cke_40_label"
								class="cke_button_label cke_button__preview_label"
								aria-hidden="false">Preview</span></a></span><span class="cke_toolbar_end"></span></span></span></span>
			<div id="cke_1_contents" class="cke_contents cke_reset"
				role="presentation" style="height: 280px;">
				<span id="cke_45" class="cke_voice_label">Press ALT 0 for
					help</span>
				<iframe src="" frameborder="0" class="cke_wysiwyg_frame cke_reset"
					title="Rich Text Editor, pushPopupHtmlEditor"
					aria-describedby="cke_45" tabindex="0" allowtransparency="true"
					style="width: 100%; height: 100%;"></iframe>
			</div>
			<span id="cke_1_bottom" class="cke_bottom cke_reset_all"
				role="presentation" style="-webkit-user-select: none;"><span
				id="cke_1_resizer"
				class="cke_resizer cke_resizer_vertical cke_resizer_ltr"
				title="Resize" onmousedown="CKEDITOR.tools.callFunction(0, event)">◢</span><span
				id="cke_1_path_label" class="cke_voice_label">Elements path</span><span
				id="cke_1_path" class="cke_path" role="group"
				aria-labelledby="cke_1_path_label"><span
					class="cke_path_empty">&nbsp;</span></span></span>
		</div>
	</div>

</body>
</html>
<%@ page import="jandcode.utils.UtFile; jandcode.web.*; jandcode.wax.core.utils.theme.*" %>
<%
    Tml th = this
    def f = { p -> ("/js/" + p) }

    // Собираем
    def files = [

            // shower
            f("shower/baseAppShower.js"),
            f("shower/webShower.js"),

            // theme
            f("theme/webTheme.js"),
            f("theme/sfTheme.js"),

            // controls
            f("control/subject/list.js"),

            // frames
            f("frame/subject/show.js"),
            f("frame/test.js")
    ]

    // Выводим
    th.outTml("jc/compoundFile", files: files)
%>
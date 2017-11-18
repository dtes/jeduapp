<%@ page import="jandcode.auth.AuthService; jandcode.utils.UtLang; jandcode.wax.core.utils.WaxTml" %>
<!DOCTYPE html>

<% WaxTml th = new WaxTml(this); %>

<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport"
          content="width=device-width, height=device-height, initial-scale=1, maximum-scale=1, user-scalable=0"/>
    <link rel='shortcut icon' href='${th.ref('favicon.ico')}' type='image/x-icon'/>
    <title>${args.containsKey("title")? args.title: "EduApp"}</title>

    %{--Шрифты и иконки--}%
    %{--<jc:linkCss url="fonts/text/roboto.css"/>--}%
    %{--<jc:linkCss url="fonts/fontello/css/fontello.css"/>--}%

    %{--CSS--}%
    %{--<jc:linkCss url="css/jquery-ui.min.css"/>--}%
    %{--<jc:linkCss url="css/app/app.css"/>--}%

    %{--FINEUPLOADER CSS--}%
    %{--<jc:linkCss url="js/external/fineuploader/css/fine-uploader-gallery.css"/>--}%

    %{--JS--}%
    <jc:linkJs url="js/external.js"/>
    <jc:linkJs url="js/mb-all.js"/>
    <jc:linkJs url="js/app-all.js"/>

</head>

<body>
<%@ page import="mb.core.system.MbEntity; mb.core.system.MbAttrs;" %>

<%
    MbEntity mb = args.mb;
    MbAttrs a = mb.attrs;
%>

<mb:extend path="frame/base"/>

<mb:attr name="rec" js="true"/>
<mb:attr name="jsClass" value="jQuery.mb.frame.Test"/>

<mb:body name="main">

    <div>
        Hello from test
    </div>

</mb:body>
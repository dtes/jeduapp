<%@ page import="jandcode.dbm.data.DataRecord; mb.core.utils.UtDict; jandcode.dbm.data.DataStore; mb.core.system.MbEntity; mb.core.system.MbAttrs;" %>

<%
    MbEntity mb = args.mb;
    MbAttrs a = mb.attrs;

    DataStore subjects = UtDict.getDict("Subject", mb.app).getData()
%>

<mb:extend path="frame/base"/>

<mb:attr name="rec" js="true"/>
<mb:attr name="jsClass" value="jQuery.mb.control.subject.List"/>

<mb:body name="main">

    <div>

        <ul class="subject-list">
        <% for (DataRecord subject : subjects) { %>
            <li class="subject-item" data-id="${subject.id}">${subject.name}</li>
        <% }%>
        </ul>

    </div>

</mb:body>
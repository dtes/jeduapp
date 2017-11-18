<%@ page import="jandcode.dbm.data.DataRecord; mb.core.system.MbEntity; mb.core.system.MbAttrs;" %>

<%
    MbEntity mb = args.mb;
    MbAttrs a = mb.attrs;
%>

<mb:extend path="frame/base"/>

<mb:attr name="rec" js="true"/>
<mb:attr name="jsClass" value="jQuery.mb.frame.subject.Show"/>

<mb:body name="main">

    <div>
        <ul class="chapter-list">
            <% for (DataRecord chapter : a.chapters) { %>
            <li class="chapter-item" data-id="${chapter.id}">
                ${chapter.name}
            </li>
            <% } %>
        </ul>
    </div>

</mb:body>
<%@ page import="jandcode.dbm.data.DataRecord; mb.core.utils.UtDict; jandcode.wax.core.utils.WaxTml" %>
<!DOCTYPE html>
<%
    WaxTml th = new WaxTml(this);
    th.request.noCache();
%>

<% th.includeRel("components/app/top.gsp") %>

<script>
    $.extend($.mb, {
        baseUrl: '${th.ref("/app")}/'
    });

</script>

<div id="mb-app">
    <div id="mb-content" class="container">
        <div class="row mb-ofc">
            <div id="mb-sidebar" class="mb-ofc-left">
                %{--<mb:control type="sidebar"/>--}%
            </div>

            <div id="mb-main" class="mb-ofc-right col-lg-9 col-md-9 col-sm-12">
                <mb:control type="subject/list"/>
            </div>
        </div>
    </div>

    <div id="mb-app-mask" class="mb-ofc-mask">
    </div>
</div>

<jc:linkJs url="js/webApp.js"/>

<script type="text/javascript">
    $(document).ready(function () {
        $.mb.app = $.mb.create('$.mb.WebApp', {
            themeClass: '$.mb.theme.WebTheme'
        });
    })
</script>

<% th.includeRel("components/app/bottom.gsp") %>
<% th.includeRel("templates/all.html.gsp") %>

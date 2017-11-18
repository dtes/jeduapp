<%
    /*
      genid on sequences
     */
%>

<%
    for (t in th.tables()) {
        def dd = th.dbtable(t)
        if (dd.view) continue // view пропускаем
%>
create sequence seq_${t.name} start with ${dd.genIdStart} owned by ${t.name}.id
${th.delim}
<% } %>

${th.delim}

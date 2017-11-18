<%@ page import="jandcode.lang.*" %>
<script type="text/javascript">
  Ext.define("Jc.App", {
    extend: "Jc.BaseApp",

    createMainMenu: function() {
      //
      var menu = Jc.menu;
      var item = Jc.action;
      //
      var logo = Ext.create('Jc.control.PageHeader', {
        text: Jc.ini.app.title,
        icon: Jc.url("page/logo-small.png"),
        width: 210,
        listeners: {
          click: {
            element: 'el',
            fn: function() {
              Jc.app.home();
            }
          }
        }
      });
      //
      return [logo, '-'].concat(this.createMenuForUser());
    },

    createMenuForUser: function() {
      var mm = [
        Jc.menu({text: UtLang.t('Помощь'), icon: "help", scope: this, onExec: this.help, items: [
          Jc.action({text: 'О программе', scope: this, onExec: this.about})
        ]}),
        '->'
      ];
      return mm;
    },

    home: function() {
      Jc.showFrame({frame: "Jc.Home", id: true});
    },

    help: function() {
      Jc.showFrame({
        frame: "Jc.frame.HtmlView", id: 'app-frame-help',
        title: 'Помощь',
        url: Jc.url('help/index.html')
      });
    },

    about: function() {
      Jc.showFrame({frame: "Jc.About"});
    },

    //

    __end__: null

  });
</script>

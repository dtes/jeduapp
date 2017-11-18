(function ($) {

    $.mb.define('$.mb.WebApp', {
        extends: '$.mb.baseApp',

        onInit: function () {
            var th = this;
            th.callParent(arguments);

            //
            th.sidebar = $("#mb-main-sidebar");

        },

        homeMenu: function () {
            var th = this;
            th.sidebar.trigger("toggle")
        },

        sessionExpired: function () {
            var th = this;

        },

        notify: function (msg) {
            var th = this;
            th.quickie.notify(msg);
        }
    });

})(jQuery);
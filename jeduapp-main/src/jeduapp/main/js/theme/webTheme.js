(function ($) {

    $.mb.define('$.mb.theme.WebTheme', {
        extends: '$.mb.theme.Base',

        historyClass: '$.mb.history.WebHistory',

        onInit: function () {
            var th = this;
            th.callParent(arguments);

            // Main shower
            var mainShower = $.mb.create('$.mb.shower.WebShower');
            mainShower.on({
                'mb.beforeRemove': function (e) {
                    // $.mb.app.sidebar.trigger("hide");
                },

                'mb.afterShow': function (e, frame) {
                    $.mb.app.sidebar.trigger('activate', $(frame).attr("data-sidebar-item"));
                }
            });
            th.setShower('main', mainShower);

            //

            // Sidebar
            $("#mb-main-sidebar").mbCreate('jQuery.mb.control.Sidebar', {
                shower: mainShower
            });
        },

        showLoading: function () {
            $.mb.showDialog({
                frame: $.mb.tml('mb-main-loading-template', {
                    id: 'mb-loading-frame'
                })
            });
        },

        hideLoading: function () {
            $('.mb-loading-frame-modal').modal('hide');
        }
    })

})(jQuery);
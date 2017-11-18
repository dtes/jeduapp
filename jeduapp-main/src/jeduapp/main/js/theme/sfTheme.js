(function ($) {

    $.mb.define('$.mb.theme.SfTheme', {
        extends: '$.mb.theme.Base',

        historyClass: '$.mb.history.WebHistory',

        onInit: function () {
            var th = this;
            th.callParent(arguments);

            // Main shower
            var mainShower = $.mb.create('$.mb.shower.WebShower');
            th.setShower('main', mainShower);
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
(function ($) {

    var mainSelector = '#mb-main';
    var headSelector = '#mb-toolbar-main';

    $.mb.define('$.mb.shower.WebShower', {
        extends: '$.mb.shower.BaseAppShower',

        historySupport: true,

        onInit: function (config) {
        },

        showData: function (data, showCfg) {
            var th = this;
            var content = ($.type(data) == 'string') ? $(data) : data;
            var toolbar = content.find('.mb-toolbar');

            clear(th);

            content.data('toolbar', toolbar);
            th.onShowToolbar(toolbar);

            $(mainSelector).append(content);

            content.on({
                'mb.afterok': function () {
                    if (showCfg.onOk) {
                        showCfg.onOk.apply(content.mb(), arguments);
                    }
                },

                'mb.aftercancel': function () {
                    if (showCfg.onCancel) {
                        showCfg.onCancel.apply(content.mb(), arguments);
                    }
                }
            });
        },

        showError: function (msg, errText) {
            var th = this;

            if (errText) {
                if (errText.indexOf("Need recreate") != -1) {
                    $.mb.app.onNeedRecreate();
                } else if (errText.indexOf("Need login") != -1) {
                    $.mb.app.onNeedLogin();
                }
            }

            th.showData($.mb.tml("main-err-template", {
                id: $.mb.sequence.next(),
                title: "Ошибка",
                msg: msg,
                error: errText
            }), this, {});
        },

        saveToHistoryState: function (state) {
            state.data.scrollTop = $('#mb-app').scrollTop();
        },

        recoverFromHistoryState: function (state) {
            if (state.data.scrollTop) {
                $("#mb-app").scrollTop(state.data.scrollTop);
            }
        },

        prepareFrameToHistory: function () {

        },

        onShowToolbar: function (toolbar) {
            /*$(toolbar).appendTo($(headSelector));*/
        },

        onBeforeLoad: function (showCfg) {
            var th = this;
            clear(th);
            $(mainSelector).html($.mb.tml("mb-main-loading-template"));
        },

        getToolbar: function () {
            return $(headSelector).find('.mb-toolbar');
        },

        getCurrentFrame: function () {
            return $(mainSelector).find('.mb-frame');
        }
    });

    function clear(th) {
        var frame = th.getCurrentFrame();
        var hasFrame = frame.length > 0;
        frame = hasFrame ? $(frame) : frame;

        if (hasFrame) {
            th.beforeRemove(frame);
        }

        $(mainSelector).empty();
        $(headSelector).empty();
    }

})(jQuery);
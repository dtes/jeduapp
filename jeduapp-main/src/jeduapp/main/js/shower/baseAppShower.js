(function ($) {

    $.mb.define('$.mb.shower.BaseAppShower', {
        extends: '$.mb.shower.Base',

        mainSelector: '#mb-main',
        headSelector: '#mb-toolbar-main',
        guestUsrMsg: 'Пользователь не авторизован',

        showError: function (msg, errText) {
            var th = this;

            th.showData($.mb.tml("main-err-template", {
                id: $.mb.sequence.next(),
                title: "Ошибка",
                msg: msg,
                error: errText
            }), this, {});
        },

        clear: function () {
            var th = this;
            var frame = th.getCurrentFrame();
            var hasFrame = frame.length > 0;
            frame = hasFrame ? $(frame) : frame;

            if (hasFrame) {
                th.beforeRemove(frame);
            }

            $(th.mainSelector).empty();
            $(th.headSelector).empty();
        },

        closeFrame: function (fr) {
            var th = this;
            var currFrame = th.getCurrentFrame();
            if (currFrame.attr('id') == $(fr).attr('id')) {
                th.clear();
            }
        },

        onErrorLoad: function (err, showCfg) {
            var th = this;
            if (err.responseText && err.responseText.indexOf(th.guestUsrMsg) != -1) {
                if ($.mb.app.sessionExpired) {
                    $.mb.app.sessionExpired();
                }
            }

            th.showError("Возникла ошибка при загрузке страницы:", th.getHandledErrorText(err));
        },

        onBeforeLoad: function (showCfg) {
            var th = this;
            th.clear(th);
            $(th.mainSelector).html($.mb.tml("mb-main-loading-template"));
        },

        getToolbar: function () {
            var th = this;
            return $(th.headSelector).find('.mb-toolbar');
        },

        getCurrentFrame: function () {
            var th = this;
            return $(th.mainSelector).find('.mb-frame').first();
        }

    });
})(jQuery);
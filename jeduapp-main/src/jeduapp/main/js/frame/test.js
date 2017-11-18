(function ($) {

    $.mb.define('$.mb.frame.Test', {
        extends: '$.mb.frame.Mobile',

        onInit: function() {
            console.log('test frame initialized');
        }
    });

})(jQuery);
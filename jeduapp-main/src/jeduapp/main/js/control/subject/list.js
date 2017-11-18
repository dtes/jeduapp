(function ($) {

    $.mb.define('$.mb.control.subject.List', {
        extends: '$.mb.control.Base',

        onInit: function() {
            var th = this;

            th.on({
                click: function(e) {
                    var item = $(e.currentTarget);
                    var id = item.data('id');

                    $.mb.showFrame({
                        frame: {url: $.mb.url('subject/show/' + id)}
                    });
                }
            }, '.subject-item')
        }
    });

})(jQuery);
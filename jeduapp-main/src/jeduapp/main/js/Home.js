/**
 *
 */
Ext.define('Jc.Home', {
    extend: 'Jc.Frame',

    onInit: function() {
        this.callParent(arguments);
        var th = this;
        var b = th.createBuilder();
        Ext.apply(this, {
            title: 'Добро пожаловать'
        });
        //
        th.items = [
            b.frameheader()
        ];
        //
    }

});
 
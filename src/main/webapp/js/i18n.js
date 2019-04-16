$(()=>{
    $("#locales").change(function () {
        var selectedOption = $('#locales').val();
        if (selectedOption != ''){
            window.location.replace('?lang=' + selectedOption);
        }
    });
    $.i18n().load( {
        en: {
            'edit':'Edit',
            'view': 'View',
            'delete': 'Are you sure you want to delete the selected computers?'
        },
        fr : {
            'edit': 'Editer',
            'view' : 'Voir',
            'delete' : 'Etes-vous sûr de vouloir supprimer les ordinateurs selectionnés?'
        }
    } );
})
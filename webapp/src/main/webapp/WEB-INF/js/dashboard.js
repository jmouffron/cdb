//On load
$(function () {
    // Default: hide edit mode
    $(".editMode").hide();

    // Click on "selectall" box
    $("#selectall").click(function () {
        $('.cb').prop('checked', this.checked);
    });

    // Click on a checkbox
    $(".cb").click(function () {
        if ($(".cb").length == $(".cb:checked").length) {
            $("#selectall").prop("checked", true);
        } else {
            $("#selectall").prop("checked", false);
        }
        if ($(".cb:checked").length != 0) {
            $("#deleteSelected").addClass("disabled");
        } else {
            $("#deleteSelected").removeClass("disabled");
        }
    });

});


// Function setCheckboxValues
(function ($) {

    $.fn.setCheckboxValues = function ( input, checkboxFieldName) {

        var str = $('.' + checkboxFieldName + ':checked').map(function () {
            return this.value;
        }).get().join();

        $(`#deleteForm input[name=${input}]`).val(str);

        return this;
    };

}(jQuery));

// Function toggleEditMode
(function ($) {

    $.fn.toggleEditMode = function () {
        if ($(".editMode").is(":visible")) {
            $(".editMode").hide();
            $("#editComputer").text($.fn.i18n("Delete"));
        }
        else {
            $(".editMode").show();
            $("#editComputer").text($.fn.i18n("View"));
        }
        return this;
    };

}(jQuery));


// Function delete selected: Asks for confirmation to delete selected computers,
// then submits it to the deleteForm
(function ($) {
    $.fn.deleteSelected = function () {
        if (confirm("Are you sure you want to delete the selected computers?")) {
            $('#deleteForm input[name=ids]').setCheckboxValues('ids', 'cb');
            $('#deleteForm').submit()
        }
    };
}(jQuery));

(function ($) {
    $.fn.i18n = function ( word ) {
        let locales = {
            "en": {
                'View': 'View',
                'Delete': 'Delete'
            },
            "fr": {
                'View': 'Voir',
                'Delete': 'Supprimer'
            }
        }
        let lang = navigator.language || navigator.userLanguage;

        return locales[lang.substring(0,2)][word]
    }
}(jQuery));

// Event handling
// Onkeydown
$(document).keydown(function (e) {

    switch (e.keyCode) {
        // DEL key
        case 46:
            if ($(".editMode").is(":visible") && $(".cb:checked").length != 0) {
                $.fn.deleteSelected();
            }
            break;
        // E key (CTRL+E will switch to edit mode)
        case 69:
            if (e.ctrlKey) {
                $.fn.toggleEditMode();
            }
            break;
    }
});
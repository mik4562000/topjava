const mealAjaxUrl = "meals/";
const filterAjaxUrl = "meals/filter/";
let filterForm;

const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateAjaxUrl: filterAjaxUrl
};

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
    filterForm = $('#filterForm');
});

function resetFilter() {
    filterForm.trigger("reset");
    updateTable();
}

function filter() {
    $.ajax({
        type: 'GET',
        url: ctx.updateAjaxUrl,
        data: filterForm.serialize()
    }).done(function (response) {
        ctx.datatableApi.clear().rows.add(response).draw();
    }).fail(function(jqXHR, textStatus, errorThrown) {
        console.error("AJAX error: " + textStatus, errorThrown);
    });
}
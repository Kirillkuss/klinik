function listPatient() {
    $(document).ready(function() {
    $('table tbody').on('mousedown', 'tr', function(e) {
        $(this).addClass('highlight').siblings().removeClass('highlight');
    });
    $.getJSON('http://localhost:8081/web/patients/all', function(json) {
        var tr=[];
        for (var i = 0; i < json.length; i++) {
            tr.push('<tr>');
            tr.push('<td>' + json[i].idPatient + '</td>');
            tr.push('<td>' + json[i].surname + '</td>');
            tr.push('<td>' + json[i].name + '</td>');
            tr.push('<td>' + json[i].fullName + '</td>');
            tr.push('<td>' + json[i].gender + '</td>');
            tr.push('<td>' + json[i].phone + '</td>');
            tr.push('<td>' + json[i].address + '</td>');
            tr.push('</tr>');
            }
            $('table').append($(tr.join('')));
        });
    });
};

function AddPatient() {
    $("#testForm").submit( function (event){
        event.preventDefault();
        var idPatient  = $('#idPatient').val();
        var surname    = $('#surname').val();
        var name       = $('#name').val();
        var fullName   = $('#fullName').val();
        var gender     = $('#gender').val();
        var phone      = $('#phone').val();
        var address    = $('#address').val();
        var idDocument = $('#idDocument').val();
            $.ajax({
                type: "POST",
                contentType: "application/json; charset=utf-8",
                url: "http://localhost:8081/web/patients/add/{pat}{id-document}?id=" + idDocument,
                data: JSON.stringify ({idPatient: idPatient,
                                      surname: surname,
                                      name: name,
                                      fullName: fullName,
                                      gender: gender,
                                      phone: phone,
                                      address: address}), 
                cache: false,
                success: function( json ) {
                    var tr=[];
                    tr.push('<tr>');
                    tr.push('<td>' + json[i].idPatient + '</td>');
                    tr.push('<td>' + json[i].surname + '</td>');
                    tr.push('<td>' + json[i].name + '</td>');
                    tr.push('<td>' + json[i].fullName + '</td>');
                    tr.push('<td>' + json[i].gender + '</td>');
                    tr.push('<td>' + json[i].phone + '</td>');
                    tr.push('<td>' + json[i].address + '</td>');
                    tr.push('</tr>');
                    
                    location.reload();
                }, error: function ( error ){
                    $('#errorToast').text( error.responseText ).show();
                    $('#liveToastBtn').click();
                }
        });
    });
};
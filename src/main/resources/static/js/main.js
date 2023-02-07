/**
* Template Name: NiceAdmin - v2.3.1
* Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
* Author: BootstrapMade.com
* License: https://bootstrapmade.com/license/
*/
$(document).ready(function() {


    //var sidebarToggle = document.getElementById('toggle-sidebar-button');
    //localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('toggle-sidebar'));

//         if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
//             $("body").toggleClass('toggle-sidebar');
//         }

         $(".toggle-sidebar-btn").on("click", function(event) {
             event.preventDefault();
             $("body").toggleClass('toggle-sidebar');
//             localStorage.setItem('sb|sidebar-toggle', $("body").hasClass('toggle-sidebar'));
         });

});


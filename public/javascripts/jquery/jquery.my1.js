/*! jQuery v3.4.1 | (c) JS Foundation and other contributors | jquery.org/license */
$(document).ready(function(){
  $('#menu-icon').on('click', function(){
    $('.navbar').toggleClass('expand');
    return false;
  });
});
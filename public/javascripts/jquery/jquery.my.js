/*! jQuery v3.4.1 | (c) JS Foundation and other contributors | jquery.org/license */
$(document).ready(function() {
  $("#myForm").validate({
    rules: {
      name : {
        required: true,
        minlength: 3
      },
      age: {
        required: true,
        number: true,
      }
    }
  });
});

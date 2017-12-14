$(document).ready(function () {

    // initialization
    // ==============

    var numFiles = 0;
    $("#food1-label-explain").text("3");
    $("#food2-label-explain").text("3");
    $('[data-toggle="tooltip"]').tooltip({
        container: 'body',
        placement: 'top',
        template: '<div class="tooltip" role="tooltip"><div class="tooltip-arrow" style="color: #808080; border-top-color:#808080; margin-bottom: 2px"></div><div class="tooltip-inner" style="background-color: #808080;"></div></div>'
    });

    $('input').tooltip('disable');
    $('div').tooltip('disable');

    // prevent submitting a form when a enter key pressed
    // ==================================================

    var preventSubmit = function (event) {
        if (event.keyCode == 13) {
            event.preventDefault();
            return false;
        }
    }
    //document.querySelector('form').onkeypress = preventSubmit;
    $("#summary").keypress(preventSubmit);

    // All - Controlling 4 buttons in footer(forward, backward, crop)
    // ==============================================================

    $('#forward').on("click", function () {

        var fid = $('.step').not('.hide').attr('id');
        var fnum = fid.charAt(fid.length - 1);

        if (fnum == 2) {
            $("#steps-footer").addClass('hide');
        } else if (fnum == 3) {
            $('#crop2').addClass('hide');
            $('#crop1').removeClass('hide');
            $('#backward').addClass('hide');
        } else if (fnum == 4) {
            if (numFiles == 1) {
                $('#crop2').addClass('hide');
                $('#crop1').removeClass('hide');
                $('#backward').addClass('hide');
            } else if (numFiles == 2) {
                $('#crop2').removeClass('hide');
                $('#crop1').addClass('hide');
                $('#backward').addClass('hide');
            }
        }

        if (fnum == 4) {
            if (numFiles == 1) {
                fnum = fnum * 1 - 2;
            }
        } else {
            fnum = fnum * 1 - 1;
        }

        var forward = "step" + fnum;
        $('#' + forward).removeClass('hide');
        $('#' + fid).addClass('hide');
    });

    $('#backward').on("click", function () {
        var bid = $('.step').not('.hide').attr('id');
        var bnum = bid.charAt(bid.length - 1);
        var pass = false;

        if (bnum == 4) {
            if ($("input#foodname1").val().trim() == "") {
                $('input#foodname1').tooltip('enable');
                $("input#foodname1").focus();
                $('input#foodname1').tooltip('disable');
                pass = false;
            } else if ($("input[name=foodtaste1]:checked").length == 0) {
                $('div#foodtasteLabel1').tooltip('enable');
                $('div#foodtasteLabel1').tooltip('show');
                $('div#foodtasteLabel1').tooltip('disable');
                pass = false;
            } else {
                pass = true;
            }
        } else if (bnum == 5) {
            $('.modal-dialog').css('width', 'inherit');
            if ($("input#foodname2").val().trim() == "") {
                $('input#foodname2').tooltip('enable');
                $("input#foodname2").focus();
                $('input#foodname2').tooltip('disable');
                pass = false;
            } else if ($("input[name=foodtaste2]:checked").length == 0) {
                $('div#foodtasteLabel2').tooltip('enable');
                $('div#foodtasteLabel2').tooltip('show');
                $('div#foodtasteLabel2').tooltip('disable');
                pass = false;
            } else {
                if (numFiles == 1) {
                    $('#output1re').css("margin", "10px 140px");
                }
                pass = true;
            }
        } else if (bnum == 6) {
            if ($("input#summary").val().trim() == "") {
                $('input#summary').tooltip('enable');
                $("input#summary").focus();
                $('input#summary').tooltip('disable');
                pass = false;
            } else {
                pass = true;
                var pic1 = $("#output1re").attr('src'),
                    pic2 = $("#output2re").attr('src');

                var formData = new FormData();
                var foodtaste1 = [],
                    foodtaste2 = [];

                $('input[name=foodtaste1]:checked').each(function () {
                    foodtaste1.push($(this).val());
                });
                $('input[name=foodtaste2]:checked').each(function () {
                    foodtaste2.push($(this).val());
                });

                var foodpicname = $('#fileInput').val();

                // image(1/2), foodname(2), taste(2), evaluation(1), tag(1)
                formData.append("foodNames", $('#foodname1').val());
                formData.append("foodNames", $('#foodname2').val());
                formData.append("foodTastes", foodtaste1);
                formData.append("foodTastes", foodtaste2);
                formData.append("summary", $('#summary').val());
                formData.append("tag", $('#tag').val());
                if (pic2 === undefined) {
                    formData.append('foodPictures',srcToFile(pic1, foodpicname, 'image/png'));
                } else {
                    var fpnSplit = foodpicname.split(", ");

                    formData.append('foodPictures',srcToFile(pic1, foodpicname, 'image/png'));
                    formData.append('foodPictures',srcToFile(pic2, fpnSplit[1], 'image/png'));

                }


                $.ajax({
                    type: 'POST',
                    url: '/matches/upload',
                    contentType: false,
                    processData: false,
                    data: formData,
                    enctype: "multipart/form-data",
                    success: function (data) {
                        location.href = data
                        // url 이동하는 코드 삽입 예: 1)document.location.href -> back해서 다시 submit하면 똑같은 게시물 또 업로드 가능... 전단계로 못들어오게 함.
                        // 2) formdata.submit()
                    }
                });


            }
        } else {
            $("#steps-footer").removeClass('hide');
        }

        if (pass == true) {
            bnum = bnum * 1 + 1;
            var backward = "step" + (bnum);
            $('#' + backward).removeClass('hide');
            $('#' + bid).addClass('hide');
        }

    });

    $('#crop1').on("click", function () {
        cropImage('resize-image1', $('#resize-image1'), $('#output1'), $('#output1re'));

        var bid = $('.modal-body > div').not('.hide').attr('id');
        var bnum = bid.charAt(bid.length - 1);

        if (numFiles == 1) {
            $('#backward').removeClass('hide');
            var pic1 = $("#output1").attr('src');
            $("#output2").attr("src", pic1);
            bnum = bnum * 1 + 2;
        } else if (numFiles == 2) {
            resizeableImage($('#resize-image2'));
            init();
            $('#crop2').removeClass('hide');
            bnum = bnum * 1 + 1;
        }
        var backward = "step" + (bnum);
        $('#' + backward).removeClass('hide');
        $('#' + bid).addClass('hide');
        $(this).addClass('hide');
    });

    $('#crop2').on("click", function () {
        cropImage('resize-image2', $('#resize-image2'), $('#output2'), $('#output2re'));

        var bid = $('.modal-body > div').not('.hide').attr('id');
        var bnum = bid.charAt(bid.length - 1);
        bnum = bnum * 1 + 1;

        var backward = "step" + (bnum);
        $('#' + backward).removeClass('hide');
        $('#backward').removeClass('hide');
        $('#' + bid).addClass('hide');
        $(this).addClass('hide');
    });


    // #step1 - Showing preview of uploaded image(s), preparing a next step
    // =====================================================================

    $(document).on('change', ':file', function () {
        var input = $(this),
            bfoutput1 = document.getElementById('resize-image1'),
            bfoutput2 = document.getElementById('resize-image2'),
            output1re = document.getElementById('output1re'),
            output2re = document.getElementById('output2re'),
            foodpic1name = "",
            foodpic2name = "";

        bfoutput1.src = URL.createObjectURL(input[0].files[0]);
        output1re.src = URL.createObjectURL(input[0].files[0]);
        foodpic1name = input[0].files[0].name;

        if (input[0].files.length == 1) {
            $("#output2re").addClass('hide');
            label = foodpic1name;
        } else if (input[0].files.length > 1) {
            bfoutput2.src = URL.createObjectURL(input[0].files[1]);
            output2re.src = URL.createObjectURL(input[0].files[1]);
            foodpic2name = input[0].files[1].name;
            label = foodpic1name + ", " + foodpic2name;
        }

        numFiles = input.get(0).files ? input.get(0).files.length : 1,
            input.trigger('fileselect', [numFiles, label]);

        $("#step1").addClass('hide');
        $("#step2").removeClass('hide');
        $("#steps-footer").removeClass('hide');
        $("#backward").addClass('hide');

        resizeableImage($('#resize-image1'));
        init();

    });

    // #step1 - Changing a label of file select
    // =========================================

    $(':file').on('fileselect', function (evenet, numFiles, label) {
        var input = $(this).parents('.input-group').find(':text');
        input.val(label);
    });


    // #step4, #step5 - Checking tastes of food and changing checked-mode
    // ===================================================================

    $("input[type=checkbox]").click(function () {
        var length = 0;
        var checked;

        if (this.id.includes("f1")) {
            $('div#foodtasteLabel1').tooltip('hide');
            checked = $("#f1t-container input[type=checkbox]:checked");
            length = checked.length;
        } else if (this.id.includes("f2")) {
            $('div#foodtasteLabel2').tooltip('hide');
            checked = $("#f2t-container input[type=checkbox]:checked");
            length = checked.length;
        }

        if (length > 3) {
            $(this).prop('checked', false);
        } else {
            $(this).parent().parent().find('.taste-label-explain1').text(3 - length);
        }

        if ($("#" + this.id).is(":checked") == true) {
            var src = $("#" + this.id + "i").attr("src").replace("off", "on");
            $("#" + this.id + "i").attr("src", src);
        } else {
            var src = $("#" + this.id + "i").attr("src").replace("on", "off");
            $("#" + this.id + "i").attr("src", src);
        }
    });
});

function srcToFile(src, fileName, mimeType) {
    var arr = src.split(','), mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
    while(n--){
        u8arr[n] = bstr.charCodeAt(n);
    }
    return new File([u8arr], fileName, {type:mime});
}

function download(data, filename, type) {
    var file = new Blob([data], {type: type});
    if (window.navigator.msSaveOrOpenBlob) // IE10+
        window.navigator.msSaveOrOpenBlob(file, filename);
    else { // Others
        var a = document.createElement("a"),
            url = URL.createObjectURL(file);
        a.href = url;
        a.download = filename;
        document.body.appendChild(a);
        a.click();
        setTimeout(function () {
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);
        }, 0);
    }
}

/**
 * Foodmatching Resize and Cropping 1.0.0
 * Developed by Mike Riethmuller, changed by Seongmi Jeong
 **/

var resizeableImage = function (image_target) {

    var $container,
        orig_src = new Image(),
        image_target = $(image_target).get(0),
        event_state = {},
        constrain = false,
        min_width = 60, // Change as required
        min_height = 60,
        max_width = 600, // Change as required
        max_height = 420,
        resize_canvas = document.createElement('canvas');

    // initialization
    // ==============

    // when loading a page
    // create a new image with a copy of the original src
    // When resizing, you will always use this original copy as the base
    init = function () {
        orig_src.src = image_target.src;

        // Add resize handles
        if ($(image_target).parent('.resize-container').length == 0) {
            $(image_target).wrap('<div class="resize-container"></div>')
                .before('<span class="resize-handle resize-handle-nw"></span>')
                .before('<span class="resize-handle resize-handle-ne"></span>')
                .after('<span class="resize-handle resize-handle-se"></span>')
                .after('<span class="resize-handle resize-handle-sw"></span>');
        }

        // Get a variable for the container
        $container = $(image_target).parent('.resize-container');

        // Add events
        $container.on('mousedown', '.resize-handle', startResize);
        $container.on('mousedown', 'img', startMoving);
    };

    // Resizing image(s) (Saving information when an user start and end resizing, drawing picture(s))
    // ==============================================================================================

    startResize = function (e) {
        e.preventDefault();
        e.stopPropagation();
        saveEventState(e);
        $(document).on('mousemove touchmove', resizing);
        $(document).on('mouseup touchend', endResize);
    };
    endResize = function (e) {
        e.preventDefault();
        $(document).off('mouseup touchend', endResize);
        $(document).off('mousemove touchmove', resizing);
    };

    saveEventState = function (e) {
        // initial event details and container state
        event_state.container_width = $container.width();
        event_state.container_height = $container.height();
        event_state.container_left = $container.offset().left;
        event_state.container_top = $container.offset().top;
        event_state.mouse_x = (e.clientX || e.pageX || e.originalEvent.touches[0].clientX) + $(window).scrollLeft();
        event_state.mouse_y = (e.clientY || e.pageY || e.originalEvent.touches[0].clientY) + $(window).scrollTop();

        if (typeof e.originalEvent.touches !== 'undefined') {
            event_state.touches = [];
            $.each(e.originalEvent.touches, function (i, ob) {
                event_state.touches[i] = {};
                event_state.touches[i].clientX = 0 + ob.clientX;
                event.state.touches[i].clientY = 0 + ob.clientY;
            });
        }
        event_state.event = e;
    }

    resizing = function (e) {
        var mouse = {}, width, height, left, top, offset = $container.offset();
        mouse.x = (e.clientX || e.pageX || e.originalEvent.touches[0].clientX) + $(window).scrollLeft();
        mouse.y = (e.clientY || e.pageY || e.originalEvent.touches[0].clientY) + $(window).scrollTop();

        if ($(event_state.event.target).hasClass('resize-handle-se')) {
            width = mouse.x - event_state.container_left;
            height = mouse.y - event_state.container_top;
            left = event_state.container_left;
            top = event_state.container_top;
        } else if ($(event_state.event.target).hasClass('resize-handle-sw')) {
            width = event_state.container_width - (mouse.x - event_state.container_left);
            height = mouse.y - event_state.container_top;
            left = mouse.x;
            top = event_state.container_top;
        } else if ($(event_state.event.target).hasClass('resize-handle-nw')) {
            width = event_state.container_width - (mouse.x - event_state.container_left);
            height = event_state.container_height - (mouse.y - event_state.container_top);
            left = mouse.x;
            top = mouse.y;
            if (constrain || e.shiftKey) {
                top = mouse.y - ((width / orig_src.width * orig_src.height) - height);
            }
        } else if ($(event_state.event.target).hasClass('resize-handle-ne')) {
            width = mouse.x - event_state.container_left;
            height = event_state.container_height - (mouse.y - event_state.container_top);
            left = event_state.container_left;
            top = mouse.y;
            if (constrain || e.shiftKey) {
                top = mouse.y - ((width / orig_src.width * orig_src.height) - height);
            }
        }
        // maintain ratio
        if (constrain || e.shiftKey) {
            height = width / orig_src.width * orig_src.height;
        }

        if (width > min_width && height > min_height && width < max_width && height < max_height) {
            resizeImage(width, height);

            //re-calculating the image dimensions until drag end for firefox
            $container.offset({'left': left, 'top': top});
        }
    }

    //everytime when you resize, draw picture
    resizeImage = function (width, height) {

        resize_canvas.width = width;
        resize_canvas.height = height;
        resize_canvas.getContext('2d').drawImage(orig_src, 0, 0, width, height);
        $(image_target).attr('src', resize_canvas.toDataURL("image/png"));
    };


    // Moving image(s) (Saving information when an user start and end moving, drawing picture(s))
    // ==========================================================================================

    startMoving = function (e) {
        e.preventDefault();
        e.stopPropagation();
        saveEventState(e);
        $(document).on('mousemove touchmove', moving);
        $(document).on('mouseup touchend', endMoving);
    };
    endMoving = function (e) {
        e.preventDefault();
        $(document).off('mouseup touchend', endMoving);
        $(document).off('mousemove touchmove', moving);
    };

    moving = function (e) {
        var mouse = {}, touches;
        e.preventDefault();
        e.stopPropagation();

        touches = e.originalEvent.touches;

        mouse.x = (e.clientX || e.pageX || touches[0].clientX) + $(window).scrollLeft();
        mouse.y = (e.clientY || e.pageY || touches[0].clientY) + $(window).scrollTop();
        $container.offset({
            'left': mouse.x - (event_state.mouse_x - event_state.container_left),
            'top': mouse.y - (event_state.mouse_y - event_state.container_top)
        });

        // Watch for pinch zoom gesture while moving
        if (event_state.touches && event_state.touches.length > 1 && touches.length > 1) {
            var width = event_state.container_width, height = event_state.container_height;
            var a = event_state.touches[0].clientX - event_state.touches[1].clientX;
            a = a * a;
            var b = event_state.touches[0].clientY - event_state.touches[1].clientY;
            b = b * b;
            var dist1 = Math.sqrt(a + b);

            a = e.originalEvent.touches[0].clientX - touches[1].clientX;
            a = a * a;
            b = e.originalEvent.touches[0].clientY - touches[1].clientY;
            b = b * b;
            var dist2 = Math.sqrt(a + b);

            var ratio = dist2 / dist1;

            width = width * ratio;
            height = height * ratio;
            // To improve performance you might limit how often resizeImage() is called
            resizeImage(width, height);
        }
    };
};

// Cropping image(s) - get info of last image(s) & drawing it to other image canvas
// ================================================================================

var cropImage = function (imageId, image, reimage, reimage2) {

    var img = document.getElementById(imageId);
    image = $(image).get(0),
        reimage = $(reimage).get(0);
    reimage2 = $(reimage2).get(0);

    var $container = $(image).parent('.resize-container');
    var $overlay = $(image).parent().parent().children('.overlay'),
        ratio = img.naturalWidth / img.width;
    var crop_canvas,
        left = ($overlay.offset().left - $container.offset().left) * ratio,
        top = ($overlay.offset().top - $container.offset().top) * ratio,
        width = ($overlay.width()) * ratio,
        height = ($overlay.height()) * ratio,
        changewidth = $overlay.width(),
        changeheight = $overlay.height();

    crop_canvas = document.createElement('canvas');
    crop_canvas.width = $overlay.width();
    crop_canvas.height = $overlay.height();
    crop_canvas.getContext('2d').drawImage(img, left, top, width, height, 0, 0, changewidth, changeheight);
    $(reimage).attr('src', crop_canvas.toDataURL("image/png"));
    $(reimage2).attr('src', crop_canvas.toDataURL("image/png"));
};

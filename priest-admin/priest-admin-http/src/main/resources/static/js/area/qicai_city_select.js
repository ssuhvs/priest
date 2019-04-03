function CitySelect(inputSelector,splitChar) {
    this.split = "-";
    if(typeof splitChar == "string"){
        this.split = splitChar;
    }
    this.init(inputSelector);
}
window.currentSelector = null;
CitySelect.prototype = {
    init: function (inputSelector) {
        this.inputSelector = inputSelector;
        if ($("#citySelectModalDialog").length == 0) {
            this.buildDialog();
            this.addDialogEvent();
        }
        this.addSelectorEvent(inputSelector);
    },
    buildDialog: function () {
        $(document.body).append(this.dialogHtml);
        var html = '<div id="citySelectDiv" class="citySelect">';
        for (var area in this.areas) {
            var areaId = this.areas[area].id;
            var provinces = this.areas[area].provs;

            html += '<div class="citySelectRow">';
            html += '<div class="citySelectArea"><label><input type="checkbox" id="area' + areaId + '" data-id="' + areaId + '" class="areaCbk" data-area-length="' + provinces.length + '"/>' + area + '</label></div>';
            html += '<div class="citySelectProvinceArea" id="provinceDiv' + areaId + '">';

            for (var i = 0; i < provinces.length; i++) {
                var provCode = provinces[i];
                var provName = this.areaName(provCode);
                var citys = __LocalDataCities.relations[provCode];
                html += '<div class="citySelectProvince">';
                html += '<label><input type="checkbox" value="' + provName + '" class="provinceCbk" id="province' + provCode + '" data-area="' + areaId + '" data-id="' + provCode + '" data-area-length="' + citys.length + '"/>' + provName + '<span></span></label><i class="layui-icon" data-prov="' + provCode + '">&#xe625;</i>';
                html += '<ul style="display:none;" id="cityUL' + provCode + '">';
                for (var j = 0; j < citys.length; j++) {
                    var cityCode = citys[j];
                    var cityName = this.areaName(cityCode);
                    html += '<li><label><input type="checkbox" value="' + cityName + '" class="cityCbk" data-prov="' + provCode + '"/>' + cityName + '</label></li>';
                }
                html += '</ul>';
                html += '</div>';
            }
            html += '</div>';
            html += '</div>';
        }
        html += "</div>";
        $("#citySelectBody").html(html);
    },
    addDialogEvent: function () {
        var self = this;
        // $(".citySelectProvince").hover(
        //     function () {
        //         $(this).find("ul").show();
        //     },
        //     function () {
        //         $(this).find("ul").hide();
        //     }
        // ); //鼠标滚动即显示
        $(".citySelectProvinceArea").find("i").on("click", function () {
            $(this).toggleClass("rotate180");
            $("#cityUL" + $(this).data("prov")).toggle();
        });
        $(".areaCbk").on("click", function (event) {
            var area = $(this);
            var provinces = $("#provinceDiv" + area.data("id")).find(".provinceCbk:not(:disabled)");
            provinces.prop("checked", area.prop("checked"));
            provinces.each(function () {
                self.checkProvince($(this));
            });
        });
        $(".provinceCbk").on("click", function (event) {
            var prov = $(this);
            self.checkProvince(prov);
        });
        $(".cityCbk").on("change", function (event) {
            var city = $(this);
            var checked = city.prop("checked");
            var prov = $("#province" + city.data("prov"));
            self.setProvinceState(prov);
        });
        $("#btnSubmitArea").on("click", function () {
            window.currentSelector.close();
        });
    },
    addSelectorEvent: function (inputSelector) {
        var self = this;
        $(document).on('click', inputSelector, function () {
            self.open($(this));
        });
        $(inputSelector).each(function () {
            $(this).css("min-height", "48px");
            $(this).css("height", this.scrollHeight);
        });
    },
    checkProvince: function (prov) {
        var self = this;
        var citys = $("#cityUL" + prov.data("id")).find(".cityCbk:not(:disabled)");
        citys.prop("checked", prov.prop("checked"));
        self.setProvinceState(prov);
    },
    setProvinceState: function (prov) {
        var cityCheckCount = $("#cityUL" + prov.data("id")).find(".cityCbk:checked").length;
        var showNum = prov.next("span");
        var cityCount = prov.attr("data-area-length");
        prov[0].indeterminate = false;
        if (cityCount == cityCheckCount) {
            prov.prop("checked", true);
        } else if (cityCheckCount == 0) {
            prov.prop("checked", false);
        } else {
            prov[0].indeterminate = true;
        }
        if (cityCheckCount > 0) {
            showNum.html("(" + cityCheckCount + ")");
        } else {
            showNum.html('');
        }
        var self = this;
        self.setAreaState(prov);
    },
    setAreaState: function (prov) {
        var area = $("#area" + prov.data("area"));
        var provCheckCount = $("#provinceDiv" + area.data("id")).find(".provinceCbk:checked").length;
        var provCount = area.attr("data-area-length");
        area[0].indeterminate = false;
        if (provCount == provCheckCount) {
            area.prop("checked", true);
        } else if (provCheckCount == 0) {
            area.prop("checked", false);
        } else {
            area[0].indeterminate = true;
        }
    },
    open: function (target) {
        window.currentSelector = this;
        this.activeInput = target;
        var areas = $("#citySelectModalDialog").find("input");
        $("#citySelectModalDialog").find("ul").hide();
        $("#citySelectModalDialog").find("i").removeClass("rotate180");
        $("#citySelectModalDialog").find("span").html('');
        areas.attr("checked", false);
        areas.removeAttr("disabled");
        areas.prop("indeterminate", false);
        areas.prop("data-check-length", 0);
        this.setStateOnOpen(target);
        $("#citySelectModalDialog").modal();
    },
    setStateOnOpen: function (target) {
        var disabledArea = "";
        var self = this;
        $(this.inputSelector).each(function () {
            var input = $(this);
            if (input[0] == target[0]) { //需要选中的
                if (input.val() != '') {
                    var checkedArea = input.val().split(self.split);
                    $(".provinceCbk").each(function () {
                        for (var i = 0; i < checkedArea.length; i++) {
                            if (this.value == checkedArea[i]) {
                                this.checked = true;
                                self.checkProvince($(this));
                                break;
                            }
                        }
                    });
                    $(".cityCbk").each(function () {
                        for (var i = 0; i < checkedArea.length; i++) {
                            if (this.value == checkedArea[i]) {
                                this.checked = true;
                                self.setProvinceState($("#province" + $(this).data("prov")));
                                break;
                            }
                        }
                    });
                }
            } else if (input.val() != '') { //需要锁定的
                if (disabledArea == '') {
                    disabledArea = input.val();
                } else {
                    disabledArea += self.split + input.val();
                }
            }
        });
        if (disabledArea != '') {
            var disabledAreas = disabledArea.split(self.split);
            $(".provinceCbk").each(function () {
                for (var i = 0; i < disabledAreas.length; i++) {
                    if (this.value == disabledAreas[i]) {
                        this.disabled = true;
                        $("#cityUL" + $(this).data("id")).find(".cityCbk").attr("disabled", "disabled");
                        break;
                    }
                }
            });
            $(".cityCbk").each(function () {
                for (var i = 0; i < disabledAreas.length; i++) {
                    if (this.value == disabledAreas[i]) {
                        this.disabled = true;
                        self.setProvinceState($("#province" + $(this).data("prov")));
                        break;
                    }
                }
            });
        }
    },
    close: function () {
        this.activeInput.val(this.getVal());
        this.activeInput.css("height", 48);
        this.activeInput.css("height", this.activeInput[0].scrollHeight);
    },
    getVal: function () {
        var provinces = $(".provinceCbk");
        var val = [];
        provinces.each(function () {
            if (this.indeterminate) { //不确定,取城市
                $("#cityUL" + $(this).data("id")).find(".cityCbk").each(function () {
                    if (this.checked) {
                        val.push(this.value);
                    }
                });
            } else if (this.checked) {
                val.push(this.value);
            }
        });
        return val.join(this.split);
    },
    areaName: function (code) {
        return __LocalDataCities.list[code];
    },
    areas: {
        "华东": {id: 0, provs: [310000, 320000, 330000, 340000, 360000]},
        "华北": {id: 1, provs: [110000, 120000, 130000, 140000, 370000, 150000]},
        "华中": {id: 2, provs: [430000, 420000, 410000]},
        "华南": {id: 3, provs: [440000, 350000, 460000, 450000]},
        "东北": {id: 4, provs: [210000, 220000, 230000]},
        "西北": {id: 5, provs: [610000, 620000, 630000, 650000, 640000]},
        "西南": {id: 6, provs: [500000, 510000, 530000, 520000, 540000]},
        "港澳台": {id: 7, provs: [810000, 820000, 710000]}
    },
    dialogHtml: '<div class="modal-dialog" id="citySelectModalDialog" style="width:900px;height:400px;margin-left:-450px;margin-top:-260px;left:50%;top:40%;position:fixed;z-index:2050;display:none;"><div class="modal-content"><div class="modal-header"><button aria-hidden="true" data-dismiss="modal" class="close" type="button">×</button><h3>选择地区</h3></div><div class="modal-body" id="citySelectBody" style="height:330px;"></div><div class="modal-footer"><a href="javascript:;" id="btnSubmitArea" class="btn btn-danger" data-dismiss="modal" aria-hidden="true">确定</a><a href="javascript:;" class="btn btn-default" data-dismiss="modal" aria-hidden="true">关闭</a></div></div></div>'
}
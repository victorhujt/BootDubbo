/*!
 * Distpicker v1.0.2
 * https://github.com/tshi0912/city-picker
 *
 * Copyright (c) 2014-2016 Tao Shi
 * Released under the MIT license
 *
 * Date: 2016-02-29T12:11:36.473Z
 */

(function (factory) {
    if (typeof define === 'function' && define.amd) {
        // AMD. Register as anonymous module.
        define('ChineseDistricts', [], factory);
    } else {
        // Browser globals.
        factory();
    }
})(function () {

    var ChineseDistricts = {
        "86": {
            "华北": [
            {
                "code": "110000",
                "address": "北京"
            },
            {
                "code": "120000",
                "address": "天津"
            },
            {
                "code": "130000",
                "address": "河北省"
            },
            {
                "code": "140000",
                "address": "山西省"
            },
            {
                "code": "150000",
                "address": "内蒙古自治区"
            }
        ],
            "西北": [
            {
                "code": "610000",
                "address": "陕西省"
            },
            {
                "code": "620000",
                "address": "甘肃省"
            },
            {
                "code": "630000",
                "address": "青海省"
            },
            {
                "code": "640000",
                "address": "宁夏回族自治区"
            },
            {
                "code": "650000",
                "address": "新疆维吾尔自治区"
            }
        ],
            "华东": [
            {
                "code": "310000",
                "address": "上海"
            },
            {
                "code": "320000",
                "address": "江苏省"
            },
            {
                "code": "330000",
                "address": "浙江省"
            },
            {
                "code": "340000",
                "address": "安徽省"
            },
            {
                "code": "350000",
                "address": "福建省"
            },
            {
                "code": "360000",
                "address": "江西省"
            },
            {
                "code": "370000",
                "address": "山东省"
            }
        ],
            "中南": [
            {
                "code": "410000",
                "address": "河南省"
            },
            {
                "code": "420000",
                "address": "湖北省"
            },
            {
                "code": "430000",
                "address": "湖南省"
            },
            {
                "code": "440000",
                "address": "广东省"
            },
            {
                "code": "450000",
                "address": "广西壮族自治区"
            },
            {
                "code": "460000",
                "address": "海南省"
            }
        ],
            "东北": [
            {
                "code": "210000",
                "address": "辽宁省"
            },
            {
                "code": "220000",
                "address": "吉林省"
            },
            {
                "code": "230000",
                "address": "黑龙江省"
            }
        ],
            "西南": [
            {
                "code": "500000",
                "address": "重庆"
            },
            {
                "code": "510000",
                "address": "四川省"
            },
            {
                "code": "520000",
                "address": "贵州省"
            },
            {
                "code": "530000",
                "address": "云南省"
            },
            {
                "code": "540000",
                "address": "西藏自治区"
            }
        ],
            "港澳台": [
            {
                "code": "710000",
                "address": "台湾"
            },
            {
                "code": "810000",
                "address": "香港特别行政区"
            },
            {
                "code": "820000",
                "address": "澳门特别行政区"
            }
        ]
        }
        }
        ;

    if (typeof window !== 'undefined') {
        window.ChineseDistricts = ChineseDistricts;
    }

    return ChineseDistricts;

});
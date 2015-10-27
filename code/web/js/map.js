// 使用百度地图API

/******************** global data structure and variable ********************/

// map object
var map;
// all provinces name(unused)
var provinces = [];
// all travel info
var allTravelInfos = new Array();
// all travel route
var allTravelRoutes = new Array();


// information window for all marker
var allRoutesInfoWindows = new Array();
// all provinces plygon
var allProvincePlygon = new Array();


// current new added route
var newRoute;
// information windows for new added route
var newRouteInfoWindows = new Array();
// travel point for new added route
var newRoutePoints = new Array();
// route marker for new added route
var newRouteMarkers = new Array();
// 是否可以添加新路线
var isNewRouteCreatable = true;


/******************** build map ********************/

window.onload = function() {
    loadMap("map-container");
}

var isMapBuilt = false;
function buildForTag(targetTag) {
    if (isMapBuilt)
        return;
    getAllTravelDataFromServer(targetTag);
};

/******************** main fuction to build a map ********************/
// load map
function loadMap(targetTag) {
    map = new BMap.Map(targetTag);
    isMapBuilt = true;
    map.centerAndZoom("中国", 5);
    map.enableScrollWheelZoom();
    map.disableDoubleClickZoom();
    setControls();
    console.log("map loaded.");
}

// get user travel data from server
function getAllTravelDataFromServer(targetTag) {
    var xmlhttp;
    if (window.XMLHttpRequest)
      {// code for IE7+, Firefox, Chrome, Opera, Safari
      xmlhttp=new XMLHttpRequest();
      }
    else
      {// code for IE6, IE5
      xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
      }
    xmlhttp.onreadystatechange=function()
      {
      if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
            var mapSetfocus = false;
            jsonArray = eval(xmlhttp.responseText)
            console.log("get data finished.");
            setAllLocalDataFromJsonArray(jsonArray);
            loadMap(targetTag);
            setMapInformation();
            registerAllItemEventListener();
        }
      }
    xmlhttp.open("GET","/travel/paths/", true);
    xmlhttp.send();
}

// set travel route and marker, some event listener will also be registered
function setMapInformation() {
    getBoundarys(provinces);
    setAllRoutes();
    console.log("set information finished.");
}

// register all event listener
function registerAllItemEventListener() {
    addMapEventListeners();
    console.log("register finished");
}

/*****************************************************/
/******************** extra function ********************/
/*****************************************************/

/******************** get and set province boundary ********************/

function getBoundarys(provincesPara) {
    for(var i = 0; i < provincesPara.length; i++) {
        getBoundary(provincesPara[i]);
    }
}

function getBoundary(provincePara){
    var bdary = new BMap.Boundary();
    bdary.get(provincePara, function(rs){       //获取行政区域
        var count = rs.boundaries.length; //行政区域的点有多少个
        for(var i = 0; i < count; i++){
            var ply;
            ply = new BMap.Polygon(rs.boundaries[i], {strokeWeight: 2, strokeColor: "#ff0000"}); //建立visited多边形覆盖物
            ply.setFillOpacity(0.2);
            ply.provinceName = provincePara;
            map.addOverlay(ply);  //添加覆盖物
            allProvincePlygon.push(ply);
            ply.addEventListener("click", function() {
                map.centerAndZoom(this.provinceName, 7);
            });
        } 
    }); 
}


/******************** get and set information windows ********************/

function getInfoWindowContent(title, src, discription, time) {
    timeLocal = time.slice(0, time.indexOf("+"));
    var sContent =
        "<div class='post-tag post-tag-time'>" +
                               " <span class='fui-time'></span>" +
                                "<a href='#''><span>" + timeLocal +"</span></a>" +
        "</div>" +
        "<h4 style='margin:0 0 5px 0;padding:0.2em 0'>" + title + "</h4>" + 
        "<img style='float:right;margin:4px' id='imgDemo' src='/media/" + src + "' width='128' height='128' title='" + title + "'/>" + 
        "<p style='margin:0;line-height:1.5;font-size:16px;text-indent:2em;'>" + discription + "</p>" + 
        "</div>";
    return sContent;
}
function getInfoWindowChangerContent() {
    var sContent = $('#add-travel-item').html();
    return sContent;
}

/******************** set exiting Route ********************/

function setAllRoutes() {
    for (var i = 0; i < allTravelRoutes.length; i++) {
        var travelRouteLocal = allTravelRoutes[i];
        var indexLocal = allRoutesInfoWindows.push(new Array()) - 1;
        for (var c = 0; c < travelRouteLocal.length; c++) {
            var routeMarker = new BMap.Marker(travelRouteLocal[c]);
            map.addOverlay(routeMarker);
            
            allRoutesInfoWindows[indexLocal].push(createMapInfoWindow(allTravelInfos[i][c]));
            
            routeMarker.addEventListener("click", function(indexI, indexC) {
                return function() {
                    map.centerAndZoom(allTravelRoutes[indexI][indexC], 15);
                    this.openInfoWindow(allRoutesInfoWindows[indexI][indexC]);
                }
                //图片加载完毕重绘infowindow
                /*document.getElementById('imgDemo').onload = function (){
                    infoWindow.redraw();   //防止在网速较慢，图片未加载时，生成的信息框高度比图片的总高度小，导致图片部分被隐藏
                }*/
            }(i, c));
        }
        var routeLineLocal = new BMap.Polyline(travelRouteLocal, {strokeColor: "green", strokeWeight: 4, strokeOpacity: 0.5});
        map.addOverlay(routeLineLocal);
    }
}

function createMapInfoWindow(infoParam) {
    return new BMap.InfoWindow(getInfoWindowContent(infoParam.title, infoParam.src, infoParam.discription, infoParam.time));
}

/******************** create new Route ********************/

function paintNewRoute() {
    map.removeOverlay(newRoute);
    newRoute = new BMap.Polyline(newRoutePoints, {strokeColor:"green", strokeWeight:4, strokeOpacity:0.5});
    map.addOverlay(newRoute);
}

function addMapEventListeners() {
    map.addEventListener("rightclick", function(d) {
        if (!isNewRouteCreatable)
            return;
        var marker = new BMap.Marker(d.point);  // 创建标注
        newRouteMarkers.push(marker);
        var indexLocal = newRoutePoints.push(d.point);
        paintNewRoute();
        map.addOverlay(marker);               // 将标注添加到地图中
        marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
        marker.enableDragging();

        var tempInfoWindow = new BMap.InfoWindow(getInfoWindowChangerContent());
        newRouteInfoWindows.push(tempInfoWindow);
        marker.infomationWindow = tempInfoWindow;
        marker.addEventListener("click", function(markerPosition) {
            return function() {
            	this.openInfoWindow(tempInfoWindow);
            	$('#longtitudeInput').val(this.point.lng);
            	$('#latitudeInput').val(this.point.lat);
            }
        }(d.point));
        marker.addEventListener("dragend", function(indexOfTarget) {
            return function(positionPara) {
                newRoutePoints[indexOfTarget] = positionPara.point;
                paintNewRoute();
            }
        }(indexLocal - 1));
    });
}

/******************** html event ********************/

function imageSelected(imagePath) {
    //var imageLocal = document.getElementById("newSelectedImage");
    //imageLocal.src = imagePath;
}

/******************** 2015年6月3日新添加 ********************/

// 查看省级和查看全国的按钮
function ZoomControl(){
      // 默认停靠位置和偏移量
      this.defaultAnchor = BMAP_ANCHOR_BOTTOM_RIGHT;
      this.defaultOffset = new BMap.Size(10, 10);
}
ZoomControl.prototype = new BMap.Control();
ZoomControl.prototype.initialize = function(map){
    // 创建一个DOM元素
    var div = document.createElement("div");
    // 添加按钮
    var nationViewBtn = document.createElement("button");
    nationViewBtn.innerHTML = "查看全国";
    nationViewBtn.setAttribute("class", "btn btn-default btns");
    var provinceViewBtn = document.createElement("button");
    provinceViewBtn.innerHTML = "查看省级";
    provinceViewBtn.setAttribute("class", "btn btn-default btns");
    
    nationViewBtn.style.margin = "4px";
    nationViewBtn.onclick = function() {
        map.centerAndZoom("中国", 4);
    }
    
    provinceViewBtn.onclick = function() {
        map.setZoom(6);
    }
    
    div.appendChild(provinceViewBtn);
    div.appendChild(nationViewBtn);
    // 设置样式
    div.style.cursor = "pointer";
    map.getContainer().appendChild(div);
    // 将DOM元素返回
    return div;
}

// 新建旅游路线的按钮

function newRouteControl(){
      // 默认停靠位置和偏移量
      this.defaultAnchor = BMAP_ANCHOR_TOP_RIGHT;
      this.defaultOffset = new BMap.Size(10, 10);
}
newRouteControl.prototype = new BMap.Control();
newRouteControl.prototype.initialize = function(map){
    // 创建一个DOM元素
    var div = document.createElement("div");
    // 添加按钮
    var newRouteStartBtn = document.createElement("button");
    newRouteStartBtn.innerHTML = "开始新旅行";
    newRouteStartBtn.setAttribute("class", "btn btn-default btns");
    var newRouteEndBtn = document.createElement("button");
    newRouteEndBtn.innerHTML = "结束当前旅行";
    newRouteEndBtn.setAttribute("class", "btn btn-default btns");
    
    newRouteStartBtn.style.margin = "4px";
    newRouteStartBtn.onclick = function() {
        isNewRouteCreatable = true;
        if (!isNewRouteCreatable)
        	return;
        startNewTravel();
    }
    
    newRouteEndBtn.onclick = function() {
        isNewRouteCreatable = false;
        endCurrentTravel();
    }
    
    div.appendChild(newRouteEndBtn);
    div.appendChild(newRouteStartBtn);
    // 设置样式
    div.style.cursor = "pointer";
    // 添加DOM元素到地图中
    map.getContainer().appendChild(div);
    // 将DOM元素返回
    return div;
}

// 设置地图控件
function setControls() {
    // 创建控件
    var zoomControlLocal = new ZoomControl();
    //var newRouteControlLocal = new newRouteControl();
    // 添加到地图当中
    map.addControl(zoomControlLocal);
    //map.addControl(newRouteControlLocal);
    console.log("control set.");
}

/******************** json文本与本地数据对象转换 ********************/
// 解析全部旅行的json为本地数据对象
function setAllLocalDataFromJsonArray(jsonArray) {
    for (var i = 0; i < jsonArray.length; i++) {
        addLocalDataFromJsonArray(jsonArray[i]);
    }
}

function addLocalDataFromJsonArray(jsonArray) {
    var pointArrayLocal = new Array();
    var infoArrayLocal = new Array();
    for (var i = 0; i < jsonArray.length; i++) {
        addLocalDataFromSimgleJsonObj(jsonArray[i], pointArrayLocal, infoArrayLocal);
    }
    allTravelRoutes.push(pointArrayLocal);
    allTravelInfos.push(infoArrayLocal);
}

function addLocalDataFromSimgleJsonObj(jsonObj, pointArray, infoArray) {
    pointArray.push(new BMap.Point(jsonObj.longtitude, jsonObj.latitude));
    var routeInfoLocal = {title: "",  src: jsonObj.picture,  discription: jsonObj.content, time: jsonObj.time};
    infoArray.push(routeInfoLocal);
}

// send start/end request for a travel

function startNewTravel() {
   var xmlhttp;
    if (window.XMLHttpRequest)
      {// code for IE7+, Firefox, Chrome, Opera, Safari
      xmlhttp=new XMLHttpRequest();
      }
    else
      {// code for IE6, IE5
      xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
      }
    xmlhttp.onreadystatechange=function()
      {
      if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
        }
      }
    xmlhttp.open("POST","/travel/new/", true);
    xmlhttp.send();
}
function endCurrentTravel() {
  var xmlhttp;
    if (window.XMLHttpRequest)
      {// code for IE7+, Firefox, Chrome, Opera, Safari
      xmlhttp=new XMLHttpRequest();
      }
    else
      {// code for IE6, IE5
      xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
      }
    xmlhttp.onreadystatechange=function()
      {
      if (xmlhttp.readyState==4 && xmlhttp.status==200)
        {
        }
      }
    xmlhttp.open("POST","/travel/end/", true);
    xmlhttp.send();
}



function SetupMap(){
    var map = new OpenLayers.Map( 'map' );

    map.addControl(new OpenLayers.Control.MouseDefaults());
    var topo2 = new OpenLayers.Layer.WMS(
       "Topografisk norgeskart2","http://opencache.statkart.no/gatekeeper/gk/gk.open?",
       {layers: 'topo2', format: 'image/jpeg'},{attribution:'<a href="http://www.statkart.no">Statens kartverk</a>, <a href="http://www.statkart.no/nor/Land/Fagomrader/Geovekst/">Geovekst</a> og <a href="http://www.statkart.no/?module=Articles;action=Article.publicShow;ID=14194">kommuner</a>'}
    );

    map.addLayers([topo2]);
    map.setCenter(new OpenLayers.LonLat(10.10, 60.1),12); 

    return map;

}

function DrawPoint(map, pointId) {

    $.post("/api/place?id=" + pointId, null, function(a, b, c){
        var place = eval(a)

        var markers = new OpenLayers.Layer.Markers( "Markers_" + pointId );
        map.addLayer(markers);

        var size = new OpenLayers.Size(21,25);
        var offset = new OpenLayers.Pixel(-(size.w/2), -size.h);
        var icon = new OpenLayers.Icon('http://www.openlayers.org/dev/img/marker.png', size, offset);
        markers.addMarker(new OpenLayers.Marker(new OpenLayers.LonLat(place.lat, place.lon),icon));
    })
}

function centerAt(lat, lon){
    map.setCenter(new OpenLayers.LonLat(lat, lon))
}

function ClickHandler(map, fn){
    OpenLayers.Control.Click = OpenLayers.Class(OpenLayers.Control, {
        defaultHandlerOptions: {
          'single': true,
          'double': false,
          'pixelTolerance': 0,
          'stopSingle': false,
          'stopDouble': false
        },

        initialize: function(closjure) {
          this.closjure = closjure
          this.handlerOptions = OpenLayers.Util.extend(
          {}, this.defaultHandlerOptions
        );
          OpenLayers.Control.prototype.initialize.apply(
          this, arguments
        );
          this.handler = new OpenLayers.Handler.Click(
          this, {
            'click': this.trigger
          }, this.handlerOptions
        );
        },

        trigger: function(e) {
          var lonlat = map.getLonLatFromViewPortPx(e.xy);
          this.closjure(lonlat.lon, lonlat.lat)
        }

      });

      var click = new OpenLayers.Control.Click(function(lat, lon){
        fn(lat, lon)
      });

      return click;
}
<script setup>
import { ref, onBeforeUnmount, onMounted, watch, defineProps } from 'vue';

const props = defineProps({
  aptList: {
    type: Array,
    required: true,
    default: () => []
  }
});

const map = ref(null);
const nearList = ref([]);

const loadMap = () => {
  window.kakao.maps.load(() => {
    const container = document.getElementById('map');
    const options = {
      center: new kakao.maps.LatLng(33.450701, 126.570667),
      level: 4
    };

    map.value = new kakao.maps.Map(container, options);

    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(position => {
        const currentPosition = new kakao.maps.LatLng(
          position.coords.latitude,
          position.coords.longitude
        );
        new kakao.maps.Marker({
          position: currentPosition,
          map: map.value
        });
        map.value.setCenter(currentPosition);
      });
    }

    addMarkers();
  });
};

const addMarkers = () => {
  if (!map.value || !props.aptList.length) return;

  const bounds = new kakao.maps.LatLngBounds();

  props.aptList.forEach(apt => {
    const markerPosition = new kakao.maps.LatLng(apt.lat, apt.lng);
    const marker = new kakao.maps.Marker({
      position: markerPosition,
      map: map.value,
      title: apt.text
    });

    const infowindow = new kakao.maps.InfoWindow({
      content: `<div style="padding:5px;">${apt.text}</div>`
    });
    infowindow.open(map.value, marker);

    bounds.extend(markerPosition);

    // 각 아파트 위치에서 카페를 검색
    searchNearbyCafes(markerPosition);
  });

  map.value.setBounds(bounds);
};

const searchNearbyCafes = (position) => {
  const ps = new kakao.maps.services.Places();
  ps.keywordSearch('카페', (data, status) => {
    if (status === kakao.maps.services.Status.OK) {
      data.forEach(place => {
        const markerPosition = new kakao.maps.LatLng(place.y, place.x);
        const marker = new kakao.maps.Marker({
          position: markerPosition,
          map: map.value,
          title: place.place_name
        });

        const infowindow = new kakao.maps.InfoWindow({
          content: `<div style="padding:5px;">⭐${place.place_name}</div>`
        });
        infowindow.open(map.value, marker);

        nearList.value.push({
          lat: place.y,
          lng: place.x,
          name: place.place_name,
          marker: marker
        });
      });
    }
  }, { location: position, radius: 500 }); // 위치 기준으로 500m 반경 내에서 검색
};

watch(() => props.aptList, () => {
  addMarkers();
}, { immediate: true });

onMounted(() => {
  loadMap();
});

onBeforeUnmount(() => {
  map.value = null;
});
</script>

<template>
  <div id="map" class="w-full h-screen"></div>
</template>

<style scoped>
#map {
  width: 100%;
  height: 100vh;
}
</style>

<template>
  <div id="map"></div>
</template>

<script setup>
import { ref, onBeforeUnmount, onMounted, watch } from 'vue';

// AppMap.vue에서 aptList 받기,,,
const props = defineProps({
  aptList: {
    type: Array,
    required: true
  }
});

const map = ref(null);
let scriptLoaded = false; // 스크립트 로드 여부를 확인하기 위한 변수
let currentLocationMarker = null; // 현재 위치 마커

const loadMap = () => {
  if (!window.kakao || !window.kakao.maps) {
    if (!scriptLoaded) {
      loadScript();
    } else {
      console.error('Kakao maps script failed to load.');
    }
    return;
  }

  if (!map.value) {
    const container = document.getElementById('map');
    const options = {
      center: new window.kakao.maps.LatLng(33.450701, 126.570667),
      level: 3
    };
    map.value = new window.kakao.maps.Map(container, options);

    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(position => {
        const currentPosition = new window.kakao.maps.LatLng(
          position.coords.latitude,
          position.coords.longitude
        );
        currentLocationMarker = new window.kakao.maps.Marker({
          position: currentPosition,
          map: map.value
        });
      });
    }
  }
};

const loadScript = () => {
  if (document.getElementById('kakao-map-script')) return; // 이미 스크립트가 로드된 경우 중복 로드 방지

  const script = document.createElement('script');
  script.id = 'kakao-map-script';
  script.src =
    '//dapi.kakao.com/v2/maps/sdk.js?appkey=eb44bbe512c97ded449fc6692128b411&autoload=false';
  script.onload = () => {
    window.kakao.maps.load(loadMap);
    scriptLoaded = true; // 스크립트 로드됨 표시
  };
  script.onerror = () => {
    console.error('Failed to load Kakao maps script.');
  };

  document.head.appendChild(script);
};

// const loadMarker = (options) => {
//   if (!map.value) return;

//   const markerPosition = options.center;

//   const marker = new window.kakao.maps.Marker({
//     position: markerPosition,
//   });

//   marker.setMap(map.value);

//   const iwContent = '<div style="padding:5px;">Hello World! <br>\
//                         <a href="https://map.kakao.com/link/map/Hello World!,33.450701,126.570667" style="color:blue" target="_blank">큰지도보기</a> <a href="https://map.kakao.com/link/to/Hello World!,33.450701,126.570667" style="color:blue" target="_blank">길찾기\
//                         </a>\
//                       </div>';

//   // 인포윈도우를 생성합니다
//   const infowindow = new kakao.maps.InfoWindow({
//     position: markerPosition,
//     content: iwContent

//   });
//   infowindow.open(map.value, marker)
// };

const addMarkers = () => {
  if (!map.value || !props.aptList.length) return;

  props.aptList.forEach(apt => {
    const markerPosition = new window.kakao.maps.LatLng(apt.lat, apt.lng);
    const marker = new window.kakao.maps.Marker({
      position: markerPosition
    });

    marker.setMap(map.value);

    const iwContent = `<div style="padding:5px;">${apt.text}</div>`;
    const infowindow = new window.kakao.maps.InfoWindow({
      position: markerPosition,
      content: iwContent
    });
    infowindow.open(map.value, marker);
  });
};

watch(() => props.aptList, () => {
  addMarkers();
});

onMounted(() => {
  loadMap();
});

onBeforeUnmount(() => {
  map.value = null; // 페이지를 떠날 때 맵 파괴
});
</script>

<style scoped></style>
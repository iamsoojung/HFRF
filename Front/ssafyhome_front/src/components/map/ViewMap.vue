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

  console.log("apt 마커 찍는다?")
  props.aptList.forEach(apt => {
    const markerPosition = new kakao.maps.LatLng(apt.lat, apt.lng);
    // console.log(apt.lat, apt.lng);
    const marker = new kakao.maps.Marker({
      position: markerPosition,
      map: map.value,
      title: apt.text
    });

    // 아파트 이름을 표시할 정보 창 생성
    const infowindow = new kakao.maps.InfoWindow({
      content: `<div style="padding:5px;">${apt.text}</div>`
    });
    infowindow.open(map.value, marker);

    // 마커 위치를 경계에 추가
    bounds.extend(markerPosition);
  });
  // 모든 마커가 보이도록 지도 경계를 설정
  map.value.setBounds(bounds);
};

watch(() => props.aptList, () => {
  addMarkers()
}, { immediate: true }  // aptList 변경될 때 or 컴포넌트 마운트될 때 실행되도록!!
);

onMounted(() => {
  loadMap();
});

onBeforeUnmount(() => {
  map.value = null;
});
</script>

<template>
  <div id="map"></div>
</template>

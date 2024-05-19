<script setup>
import { ref, onBeforeUnmount, onMounted, watch, defineProps } from 'vue';
import { KakaoMap, KakaoMapMarker, KakaoMapPolyline } from 'vue3-kakao-maps';

const props = defineProps({
  aptList: {
    type: Array,
    required: true
  }
});

const map = ref(null);

const loadMap = () => {

    window.kakao.maps.load(() => {
    const container = document.getElementById('map');
    const options = {
      center: new window.kakao.maps.LatLng(33.450701, 126.570667),
      level: 3
    };
    
      console.log("kakao object:", kakao); // 확인용 로그
      console.log("container", container)
      console.log(options)
      map.value = new window.kakao.maps.Map(container, options);

      // if (navigator.geolocation) {
      //   navigator.geolocation.getCurrentPosition(position => {
      //     const currentPosition = new kakao.maps.LatLng(
      //       position.coords.latitude,
      //       position.coords.longitude
      //     );
      //     new kakao.maps.Marker({
      //       position: currentPosition,
      //       map: map.value
      //     });
      //   });
      // }
    });
};

const addMarkers = () => {
  if (!map.value || !props.aptList.length) return;

  props.aptList.forEach(apt => {
    const markerPosition = new kakao.maps.LatLng(apt.lat, apt.lng);
    new kakao.maps.Marker({
      position: markerPosition,
      map: map.value,
      title: apt.text
    });
  });
};

watch(() => props.aptList, () => {
  addMarkers();
});

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

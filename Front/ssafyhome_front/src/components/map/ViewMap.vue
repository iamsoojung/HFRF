<script setup>
import { toRefs, ref, onMounted, defineProps, watch } from 'vue'
import { KakaoMap, KakaoMapMarker, KakaoMapCustomOverlay } from 'vue3-kakao-maps'
const props = defineProps({
  aptList: {
    type: Array,
    required: true,
    default: () => []
  }
});

const aptList = toRefs(props).aptList

onMounted(() => {
  loadCurrentPosition();
})

const coordinate = ref({
  lat: 33.450701,
  lng: 126.570667
})

const loadCurrentPosition = () => {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(position => {
      coordinate.value.lat = position.coords.latitude
      coordinate.value.lng = position.coords.longitude
    })
  }
}
const map = ref();
const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
}
const markerInfoList = ref([]);
const customOverlays = ref([]);
const onClickKakaoMapMarker = (key) => {
  customOverlays.value.forEach((overlay) => {
    if (key == overlay.key) {
      overlay.visible = !overlay.visible
    }
  })
};

let bounds;
watch(aptList, (newValue) => {
  let options = [];
  let overlay = [];
  let id = 1;
  newValue.forEach((apt) => {
    options.push({ key: id, lat: apt.lat, lng: apt.lng });
    overlay.push({
      key: id++, lat: apt.lat, lng: apt.lng, content: ` <div
        style="
          padding: 10px;
          background-color: white;
          border: 1px solid #ccc;
          border-radius: 5px;
          display: flex;
          flex-direction: column;
          align-items: flex-start;
        "
      >
      <div class=w-52>
        <div style="font-weight: bold; margin-bottom: 5px">${apt.text}</div>
          <div style="display: flex">
          <div style="margin-right: 10px">
            <img class="border-[1px]" src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/thumnail.png" width="73" height="70" />
          </div>
          <div style="display: flex; flex-direction: column; align-items: flex-start">
            <div style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap">${apt.gugunName} ${apt.dong}</div>
            <div style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap">매매가</div>
          </div>
        </div>
      </div>
      </div>`,
      yAnchor: "1.4",
      visible: false,
      aptCode: apt.value
    })
  });
  markerInfoList.value = options;
  customOverlays.value = overlay;
  bounds = new kakao.maps.LatLngBounds();
  let point;
  markerInfoList.value.forEach((markerInfo) => {
    point = new kakao.maps.LatLng(markerInfo.lat, markerInfo.lng);
  })
  if (point != null) {
    bounds.extend(point)
    setBounds()
  }
}, { deep: true })

const setBounds = () => {
  if (map.value !== undefined) {
    map.value.setBounds(bounds);
    let level = map.value.getLevel();
    map.value.setLevel(level + 2)
  }
}
</script>

<template>
  <KakaoMap width="100%" height="100%" :lat="coordinate.lat" :lng="coordinate.lng" :draggable="true"
    @onLoadKakaoMap="onLoadKakaoMap">
    <KakaoMapMarker v-for="marker in markerInfoList" @onClickKakaoMapMarker="onClickKakaoMapMarker(marker.key)"
      :clickable="true" :key="marker.key" :lng="marker.lng" :lat="marker.lat" />
    <template v-for="overlay in customOverlays" :key="overlay.key">
      <KakaoMapCustomOverlay v-if="overlay.visible" :lng="overlay.lng" :lat="overlay.lat" :content="overlay.content"
        :yAnchor="overlay.yAnchor" />
    </template>
  </KakaoMap>
</template>
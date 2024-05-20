<!-- <script setup>

const map = ref(null);
const nearList = ref([]);

const visible = ref(false);
const onClickKakaoMapMarker = () => {
  visible.value = !visible.value;
};

const addMarkers = () => {
  if (!map.value || !props.aptList.length) return;

  const bounds = new kakao.maps.LatLngBounds();

  props.aptList.forEach(apt => {
    const markerPosition = new kakao.maps.LatLng(apt.lat, apt.lng);
    const marker = new kakao.maps.Marker({
      position: markerPosition,
      map: map.value,
      title: apt.text,
    });
    const infowindow = new kakao.maps.InfoWindow({
      content: `
      <div class="p-5">
        <div style="font-weight: bold; margin-bottom: 5px">${apt.text}</div>
        <div style="display: flex">
          <div style="margin-right: 10px">
            <img src="@/assets/home.jpg" width="73" height="70" />
          </div>
          <div style="display: flex; flex-direction: column; align-items: flex-start">
            <div style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap">${apt.text}</div>
            <div style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap">${apt.text}</div>
          </div>
        </div>
      </div>
      </div>`
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
</style> -->

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
const closeOverlay = (id) => {
  customOverlays.value.forEach((overlay) => {
    if(id == overlay.id) {
      overlay.visible = false
    }
  })
};
const onClickKakaoMapMarker = (id) => {
  customOverlays.value.forEach((overlay) => {
    if(id == overlay.id) {
      overlay.visible = true
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
    overlay.push({ key: id++, lat: apt.lat, lng: apt.lng, content: ` <div
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
        <div style="font-weight: bold; margin-bottom: 5px">카카오 스페이스닷원</div>
        <div style="display: flex">
          <div style="margin-right: 10px">
            <img src="https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/thumnail.png" width="73" height="70" />
          </div>
          <div style="display: flex; flex-direction: column; align-items: flex-start">
            <div style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap">제주특별자치도 제주시 첨단로 242</div>
            <div style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap">(우) 63309 (지번) 영평동 2181</div>
            <div><a href="https://www.kakaocorp.com/main" target="_blank" style="color: blue">홈페이지</a></div>
          </div>
        </div>
      </div>`, yAnchor:"1.4", visible: false})
  });
  markerInfoList.value = options;
  customOverlays.value = overlay;
  bounds = new kakao.maps.LatLngBounds();
  let point;
  markerInfoList.value.forEach((markerInfo) => {
    point = new kakao.maps.LatLng(markerInfo.lat, markerInfo.lng);
  })
  bounds.extend(point)
  setBounds()
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
    <KakaoMapMarker v-for="marker in markerInfoList" :key="marker.key" :lng="marker.lng" :lat="marker.lat" />
    <template v-for="overlay in customOverlays" :key="overlay.key">
      <KakaoMapCustomOverlay v-show="overlay.visible" @click="onClickKakaoMapMarker(overlay.id)" :lng="overlay.lng" :lat="overlay.lat" :content="overlay.content" :yAnchor="overlay.yAnchor" />
    </template>
  </KakaoMap>
</template>
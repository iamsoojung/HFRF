<script setup>
import { toRefs, ref, onMounted, defineProps, watch } from 'vue'
import { KakaoMap, KakaoMapMarker, KakaoMapCustomOverlay } from 'vue3-kakao-maps'

const props = defineProps({
  aptList: {
    type: Array,
    required: true,
    default: () => []
  },
  searchBySelect: Boolean,
  clickAptCode: Number
});

const aptList = toRefs(props).aptList
const searchBySelect = toRefs(props).searchBySelect
const clickAptCode = toRefs(props).clickAptCode
const markerList = ref([]);
const activeCategory = ref('');
const iconList = {
  MT1: 'icon-mart.png',
  CS2: 'icon-gs25.png',
  SC4: 'icon-school.png',
  BK9: 'icon-bank.png',
  FD6: 'icon-cafe.png',
  CE7: 'icon-subway.png',
  HP8: 'icon-pharmacy.png',
  PM9: 'icon-pharmacy.png',
}

let point; // point 변수를 밖에서 선언

onMounted(() => {
  loadCurrentPosition();
  const categoryDiv = document.getElementById('category');
  const categoryButtons = categoryDiv.querySelectorAll('div');
  categoryButtons.forEach(button => {
    button.addEventListener('click', () => {
      const category = button.getAttribute('id');
      toggleCategorySearch(category);
    });
  });
});

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
const onClickKakaoMapMarker = (marker) => {
  // 클릭된 마커에 해당하는 정보를 찾습니다.
  const clickedMarker = markerInfoList.value.find(item => item.key === marker.key);
  customOverlays.value.forEach((overlay) => {
    if (marker.key == overlay.key) {
      overlay.visible = !overlay.visible
    }
  })

  if (clickedMarker) {
    // 클릭된 마커에 대한 로드맵을 표시합니다.
    const infowindow = new kakao.maps.InfoWindow({
      content: clickedMarker.content,
      removable: true
    });
    const position = new kakao.maps.LatLng(clickedMarker.lat, clickedMarker.lng);
  }
};

// 키워드 검색 완료 시 호출되는 콜백함수 입니다
const placesSearchCB = (data, status) => {
  if (status === kakao.maps.services.Status.OK) {
    const bounds = new kakao.maps.LatLngBounds();
    markerList.value = [];
    for (let marker of data) {
      const markerItem = {
        lat: marker.y,
        lng: marker.x,
        infoWindow: {
          content: marker.place_name,
          visible: false
        },
        icon: iconList[activeCategory.value] // 아이콘 설정
      };
      markerList.value.push(markerItem);
      bounds.extend(new kakao.maps.LatLng(Number(marker.y), Number(marker.x)));
    }
  }
};

//마커 클릭 시 인포윈도우의 visible 값을 반전시킵니다
const onClickMapMarker = (markerItem) => {
  if (markerItem.infoWindow?.visible !== null && markerItem.infoWindow?.visible !== undefined) {
    markerItem.infoWindow.visible = !markerItem.infoWindow.visible;
  } else {
    markerItem.infoWindow.visible = true;
  }
};

let bounds;
watch(clickAptCode, (newValue) => {
  customOverlays.value.forEach((overlay) => {
    if (overlay.aptCode == newValue) {
      overlay.visible = true
      map.value.panTo(new kakao.maps.LatLng(overlay.lat, overlay.lng))
    } else {
      overlay.visible = false
    }
  })
})
watch(searchBySelect, () => {
  let options = [];
  let overlay = [];
  let id = 1;
  aptList.value.forEach((apt) => {
    console.log(apt)
    options.push({ key: id, lat: apt.lat, lng: apt.lng });
    overlay.push({
      key: id++, lat: apt.lat, lng: apt.lng, content: ` <div
      class="rounded-xl border-blue-500 shadow-lg"
        style="
          padding: 10px;
          background-color: white;
          display: flex;
          flex-direction: column;
          align-items: flex-start;
        "
      >
      <div class="w-52 h-[70px] flex">
        <div>
          <img class="rounded-lg h-[70px] my-auto border-[1px]" src="src/assets/apartment/apartment${apt.imgNum}.jpg" width="73" height="60" />
        </div>
        <div class="ms-[10px]">
          <div class="font-bold mb-[5px] text-lg">${apt.text}</div>
          <div style="display: flex; flex-direction: column; align-items: flex-start">
            <div class="text-gray-600" style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap">${apt.gugunName} ${apt.dong}</div>
          </div>
        </div>
      </div>
      </div>`,
      yAnchor: "1.4",
      visible: apt.visible,
      aptCode: apt.value
    })
  });
  console.log(options)
  markerInfoList.value = options;
  customOverlays.value = overlay;
  bounds = new kakao.maps.LatLngBounds();
  point = null; // point 변수 초기화
  markerInfoList.value.forEach((markerInfo) => {
    point = new kakao.maps.LatLng(markerInfo.lat, markerInfo.lng);
    bounds.extend(point)
  })
  if (point != null) {
    setBounds()
    let level = map.value.getLevel();
    map.value.setLevel(level - 2)
  }
})

const setBounds = () => {
  if (map.value !== undefined) {
    map.value.setBounds(bounds);
    let level = map.value.getLevel();
    map.value.setLevel(level + 2)
  }
}

const ps = new kakao.maps.services.Places();

const toggleCategorySearch = (category) => {
  if (activeCategory.value === category) {
    activeCategory.value = '';
    markerList.value = [];
  } else {
    activeCategory.value = category;
    let keyword = '';
    switch (category) {
      case 'MT1':
        keyword = '마트';
        break;
      case 'CS2':
        keyword = '편의점';
        break;
      case 'SC4':
        keyword = '학교';
        break;
      case 'BK9':
        keyword = '은행';
        break;
      case 'FD6':
        keyword = '음식점';
        break;
      case 'CE7':
        keyword = '카페';
        break;
      case 'HP8':
        keyword = '병원';
        break;
      case 'PM9':
        keyword = '약국';
        break;
    }
    if (point != null) { // point 변수 사용
      console.log(point)
      ps.keywordSearch(keyword, placesSearchCB, { location: point, radius: 20000, useMapBounds: true });
    }
  }
}
</script>

<template>
  <KakaoMap width="100%" height="100%" :lat="coordinate.lat" :lng="coordinate.lng" :draggable="true"
    @onLoadKakaoMap="onLoadKakaoMap">
    <KakaoMapMarker v-for="marker in markerInfoList" @onClickKakaoMapMarker="onClickKakaoMapMarker(marker)"
      :clickable="true" :key="marker.key" :lng="marker.lng" :lat="marker.lat"
      :image="{ imageSrc: `src/assets/ping/icon-house.png`, imageWidth: 48, imageHeight: 48, imageOption: {} }" />
    <template v-for="overlay in customOverlays" :key="overlay.key">
      <KakaoMapCustomOverlay v-if="overlay.visible" :lng="overlay.lng" :lat="overlay.lat" :content="overlay.content"
        :yAnchor="overlay.yAnchor" />
    </template>
    <KakaoMapMarker v-for="(marker, index) in markerList" :key="marker.key === undefined ? index : marker.key"
      :lat="marker.lat" :lng="marker.lng" :infoWindow="marker.infoWindow" :clickable="true"
      :image="{ imageSrc: `src/assets/ping/${marker.icon}`, imageWidth: 48, imageHeight: 48, imageOption: {} }"
      @onClickKakaoMapMarker="onClickMapMarker(marker)" />
  </KakaoMap>
  <div id="category">
    <div id="MT1">마트</div>
    <div id="CS2">편의점</div>
    <div id="SC4">학교</div>
    <div id="BK9">은행</div>
    <div id="FD6">음식점</div>
    <div id="CE7">카페</div>
    <div id="HP8">병원</div>
    <div id="PM9">약국</div>
  </div>
</template>

<style scoped>
#category {
  position: absolute;
  top: 250px;
  right: 2px;
  border-radius: 3px;
  border: 1px solid #909090;
  box-shadow: 0 1px 1px rgba(0, 0, 0, 0.4);
  background: #fff;
  overflow: hidden;
  z-index: 1;
  font-size: 14px;
}

#category div.on {
  background: rgb(237, 237, 255);
}

#category div:hover {
  background: #eee;
  cursor: pointer;
}

#category div {
  padding: 7px 6px;
  border-bottom: 1px solid #d2d2d2;
}
</style>

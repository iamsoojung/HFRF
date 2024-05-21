<script setup>
import { toRefs, ref, onMounted, defineProps, watch } from 'vue'
import { KakaoMap, KakaoMapMarker, KakaoMapCustomOverlay } from 'vue3-kakao-maps'

const props = defineProps({
  aptList: {
    type: Array,
    required: true,
    default: () => []
  },
  clickAptCode: Number
});

const aptList = toRefs(props).aptList
const clickAptCode = toRefs(props).clickAptCode
const markerList = ref([]);
const activeCategory = ref('');
const iconList = {
  BK9: 'icon-bank.png',
  MT1: 'icon-mart.png',
  PM9: 'icon-pharmacy.png',
  SC4: 'icon-school.png',
  CE7: 'icon-cafe.png',
  SW8: 'icon-subway.png'
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
const onClickKakaoMapMarker = (key) => {
  customOverlays.value.forEach((overlay) => {
    if (key == overlay.key) {
      overlay.visible = !overlay.visible
    }
  })
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
    map.value?.setBounds(bounds);
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
      visible: apt.visible,
      aptCode: apt.value
    })
  });
  markerInfoList.value = options;
  customOverlays.value = overlay;
  bounds = new kakao.maps.LatLngBounds();
  point = null; // point 변수 초기화
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

const ps = new kakao.maps.services.Places();

const toggleCategorySearch = (category) => {
  if (activeCategory.value === category) {
    activeCategory.value = '';
    markerList.value = [];
  } else {
    activeCategory.value = category;
    let keyword = '';
    switch (category) {
      case 'BK9':
        keyword = '은행';
        break;
      case 'MT1':
        keyword = '마트';
        break;
      case 'PM9':
        keyword = '약국';
        break;
      case 'SC4':
        keyword = '학교';
        break;
      case 'CE7':
        keyword = '카페';
        break;
      case 'SW8':
        keyword = '교통';
        break;
      default:
        keyword = '맛집';
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
    <KakaoMapMarker v-for="marker in markerInfoList" @onClickKakaoMapMarker="onClickKakaoMapMarker(marker.key)"
      :clickable="true" :key="marker.key" :lng="marker.lng" :lat="marker.lat"
      :image="{ imageSrc: `src/assets/ping/icon-house.png`, imageWidth: 64, imageHeight: 64, imageOption: {} }" />
    <template v-for="overlay in customOverlays" :key="overlay.key">
      <KakaoMapCustomOverlay v-if="overlay.visible" :lng="overlay.lng" :lat="overlay.lat" :content="overlay.content"
        :yAnchor="overlay.yAnchor" />
    </template>
    <KakaoMapMarker v-for="(marker, index) in markerList" :key="marker.key === undefined ? index : marker.key"
      :lat="marker.lat" :lng="marker.lng" :infoWindow="marker.infoWindow" :clickable="true"
      :image="{ imageSrc: `src/assets/ping/${marker.icon}`, imageWidth: 64, imageHeight: 64, imageOption: {} }"
      @onClickKakaoMapMarker="onClickMapMarker(marker)" />
  </KakaoMap>
  <div id="category">
    <div id="BK9" data-order="0">
      <span class="category_bg bank"></span>
      은행
    </div>
    <div id="MT1" data-order="1">
      <span class="category_bg mart"></span>
      마트
    </div>
    <div id="PM9" data-order="2">
      <span class="category_bg pharmacy"></span>
      약국
    </div>
    <div id="SC4" data-order="3">
      <span class="category_bg school"></span>
      학교
    </div>
    <div id="CE7" data-order="4">
      <span class="category_bg cafe"></span>
      카페
    </div>
    <div id="SW8" data-order="5">
      <span class="category_bg subway"></span>
      교통
    </div>
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

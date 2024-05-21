<script setup>
import { toRefs, ref, onMounted, defineProps, watch } from 'vue'
import { KakaoMap, KakaoMapMarker, KakaoMapCustomOverlay } from 'vue3-kakao-maps'

// aptList props 받기
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

onMounted(() => {
  loadCurrentPosition();
  addCategoryClickEvent();
})

// 현재 위치 찾기
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

// 지도 생성하기
const map = ref();
const onLoadKakaoMap = (mapRef) => {
  map.value = mapRef;
  searchPlaces(); // 페이지 로드 시 장소 초기 검색

  // 지도에 idle 이벤트를 등록합니다
  kakao.maps.event.addListener(map.value, 'idle', searchPlaces);
}
const markerInfoList = ref([]);
const customOverlays = ref([]);
const onClickKakaoMapMarker = (key) => {  // 마커 클릭 시 커스텀 오버레이 보이기
  customOverlays.value.forEach((overlay) => {
    if (key == overlay.key) {
      overlay.visible = !overlay.visible
    }
  })
};

// 아파트 오버레이 띄우기
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
  let point;
  markerInfoList.value.forEach((markerInfo) => {
    point = new kakao.maps.LatLng(markerInfo.lat, markerInfo.lng);
  })
  if (point != null) {
    bounds.extend(point)
    setBounds()
  }
}, { deep: true })

// 바운드 지정
const setBounds = () => {
  if (map.value !== undefined) {
    map.value.setBounds(bounds);
    let level = map.value.getLevel();
    map.value.setLevel(level + 2)
  }
}

// ---------------------------------------------------
let placeOverlay = new kakao.maps.CustomOverlay({ zIndex: 1 });
let contentNode = document.createElement('div');  // 커스텀 오버레이의 컨텐츠 엘리먼트
let markers = []; // 마커를 담을 배열입니다
let currCategory = ''; // 현재 선택된 카테고리를 가지고 있을 변수입니다

// 장소 검색 객체를 생성합니다.
const ps = new kakao.maps.services.Places();

// 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다 
contentNode.className = 'placeinfo_wrap';

// 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
// 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다
const addEventHandle = (target, type, callback) => {
  if (target.addEventListener) {
    target.addEventListener(type, callback);
  } else {
    target.attachEvent("on" + type, callback);
  }
}
addEventHandle(contentNode, "mousedown", kakao.maps.event.preventMap);
addEventHandle(contentNode, "touchstart", kakao.maps.event.preventMap);

// 커스텀 오버레이 컨텐츠를 설정합니다
placeOverlay.setContent(contentNode);

// 각 카테고리에 클릭 이벤트를 등록합니다
const addCategoryClickEvent = () => {
  document.querySelectorAll('#category div').forEach((el) => {
    el.addEventListener('click', () => {
      const id = el.getAttribute('id');
      if (currCategory === id) {
        currCategory = '';
        markerInfoList.value = [];
        customOverlays.value = [];
        changeCategoryClass();
      } else {
        currCategory = id;
        changeCategoryClass(el);
        searchPlaces();
      }
    });
  });
}

// 카테고리 검색을 요청하는 함수입니다
const searchPlaces = () => {
  if (!currCategory) return;

  // 커스텀 오버레이를 숨깁니다.
  placeOverlay.setMap(null);

  // 지도에 표시되고 있는 마커를 제거합니다
  removeMarker();

  console.log(currCategory)
  ps.categorySearch(currCategory, placeSearchCB, { useMapBounds: true });
}

// 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
const placeSearchCB = (data, status) => {
  console.log(data, status)
  if (status === kakao.maps.services.Status.OK) {
    // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
    displayPlaces(data);
  } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
    // 검색결과가 없는경우 해야할 처리가 있다면 이곳에 작성해 주세요
  } else if (status === kakao.maps.services.Status.ERROR) {
    // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
  }
}

// 지도에 마커를 표출하는 함수입니다
const displayPlaces = (places) => {
  // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
  // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
  const order = document.getElementById(currCategory).getAttribute('data-order');

  places.forEach((place) => {
    // 마커를 생성하고 지도에 표시합니다
    const marker = addMarker(new kakao.maps.LatLng(place.y, place.x), order);

    // 마커와 검색결과 항목을 클릭 했을 때
    // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
    kakao.maps.event.addListener(marker, 'click', () => {
      displayPlaceInfo(place);
    });
  });
}

// 마커를 추가하는 함수입니다
const addMarker = (position, order) => {
  const imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png';
  const imageSize = new kakao.maps.Size(36, 37);
  const imgOptions = {
    spriteSize: new kakao.maps.Size(36, 691),
    spriteOrigin: new kakao.maps.Point(0, (order * 46) + 10),
    offset: new kakao.maps.Point(13, 37)
  };
  const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions);
  const marker = new kakao.maps.Marker({
    position,
    image: markerImage
  });
  marker.setMap(map.value);
  markers.push(marker);
  return marker;
}

// 지도에 표시되고 있는 마커를 제거하는 함수입니다
const removeMarker = () => {
  markers.forEach((marker) => {
    marker.setMap(null);
  });
  markers = [];
}

// 장소의 정보를 커스텀 오버레이로 표시하는 함수입니다
const displayPlaceInfo = (place) => {
  const content = `
    <div class="placeinfo">
      <a class="title" href="${place.place_url}" target="_blank" title="${place.place_name}">
        ${place.place_name}
      </a>  
      <span title="${place.road_address_name}">${place.road_address_name}</span>
      <span class="jibun" title="${place.address_name}">(지번) ${place.address_name}</span>
      <span class="tel">${place.phone}</span>
    </div>
    <div class="after"></div>
  `;

  contentNode.innerHTML = content;
  placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
  placeOverlay.setMap(map.value);
}

// 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수입니다
const changeCategoryClass = (el) => {
  const category = document.getElementById('category');
  const children = category.children;
  for (let i = 0; i < children.length; i++) {
    children[i].className = '';
  }

  if (el) {
    el.className = 'on';
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

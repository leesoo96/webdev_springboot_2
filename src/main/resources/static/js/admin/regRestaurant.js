const frm = document.querySelector('#frm')
async function clkGetLatLng () {
	const addr = frm.addrElem.value
	
	if(!addr) {
		alert('주소를 입력해 주세요')
		return
	}
	
	let result = await getAddrLatLng(addr)
	frm.lat.value = result.lat
	frm.lng.value = result.lng
}

function getAddrLatLng (addr) {
	return new Promise((resolve, reject) => {
		// ${addr} -> getAddrLatLng()의 @RequestParam 이름과 동일해야한다
		fetch(`/api/getAddrLatLng?addr=${addr}`)
		.then(res => res.json())
		.then(myJson => {
			if(myJson) {
				resolve(myJson) // => APIService의 return result 
			} else {
				reject()
			}
		})
	})
	
}
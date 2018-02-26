try {
    document.getElementsByClassName('breadcrumb')[0].remove()
}
catch (e) {
    console.log(e)
}
try {
    document.getElementById("header").remove();
}
catch (e) {
    console.log(e)
}
try {
    document.getElementsByClassName('header-fix')[0].remove()
    alert('script loaded')
}
catch (e) {
    console.log(e)
}
export default {
  rootUrl () {
    return '/'
  },
  release (id) {
    return '/public/release/' + id
  },
  bannerList () {
    return '/public/bannerList'
  },
  productList () {
    return '/public/productList'
  },
  releaseList () {
    return '/public/releaseList'
  },
  recruitList () {
    return '/public/recruitList'
  },
  address () {
    return '/public/address'
  }
}

export const LOAD_FILE = '/liumo/public/file/'
export const LOAD_NEWS_IMG = '/liumo/public/release/'
export const LOAD_BANNERS_IMG = '/liumo/public/banner/'

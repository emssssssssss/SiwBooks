document.addEventListener('DOMContentLoaded', () => {
  const options = {
    slidesPerView: 4,
    spaceBetween: 20,
    loop: false,
  };

  new Swiper('.swiper-fantasy', {
    ...options,
    navigation: {
      nextEl: '.swiper-fantasy .swiper-button-next',
      prevEl: '.swiper-fantasy .swiper-button-prev',
    },
  });

  new Swiper('.swiper-thriller', {
    ...options,
    navigation: {
      nextEl: '.swiper-thriller .swiper-button-next',
      prevEl: '.swiper-thriller .swiper-button-prev',
    },
  });

  new Swiper('.swiper-romance', {
    ...options,
    navigation: {
      nextEl: '.swiper-romance .swiper-button-next',
      prevEl: '.swiper-romance .swiper-button-prev',
    },
  });

  new Swiper('.swiper-horror', {
    ...options,
    navigation: {
      nextEl: '.swiper-horror .swiper-button-next',
      prevEl: '.swiper-horror .swiper-button-prev',
    },
  });
});

document.addEventListener('DOMContentLoaded', () => {
  const options = {
    slidesPerView: 4,
    spaceBetween: 20,
    loop: true,
  };

  new Swiper('.swiper-fantasy', {
    ...options,
    navigation: {
      nextEl: '.swiper-fantasy-next',
      prevEl: '.swiper-fantasy-prev',
    },
  });

  new Swiper('.swiper-thriller', {
    ...options,
    navigation: {
      nextEl: '.swiper-thriller-next',
      prevEl: '.swiper-thriller-prev',
    },
  });

  new Swiper('.swiper-romance', {
    ...options,
    navigation: {
      nextEl: '.swiper-romance-next',
      prevEl: '.swiper-romance-prev',
    },
  });

  new Swiper('.swiper-horror', {
    ...options,
    navigation: {
      nextEl: '.swiper-horror-next',
      prevEl: '.swiper-horror-prev',
    },
  });
});

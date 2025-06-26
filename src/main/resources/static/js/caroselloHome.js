document.addEventListener("DOMContentLoaded", function () {
  document.querySelectorAll(".swiper").forEach((container) => {
    new Swiper(container, {
      slidesPerView: 5,
      spaceBetween: 20,
      loop: false,
      navigation: {
        nextEl: container.querySelector(".swiper-button-next"),
        prevEl: container.querySelector(".swiper-button-prev"),
      },
      breakpoints: {
        640: { slidesPerView: 2 },
        768: { slidesPerView: 3 },
        1024: { slidesPerView: 5 },
      },
    });
  });
});

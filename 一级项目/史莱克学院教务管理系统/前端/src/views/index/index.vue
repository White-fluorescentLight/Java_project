<template>
  <div class="home-container">
    <img src="../../assets/img1.png" alt="首页大图" class="home-img">
  </div>
</template>

<style scoped>
.home-container {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center; 
  align-items: center;    
  overflow: hidden;         
  padding: 20px;             
  box-sizing: border-box;     
}

.home-img {
  max-width: 100%;          
  max-height: 100%;         
  object-fit: contain;      
  border-radius: 8px;
  opacity: 1;
}
</style>
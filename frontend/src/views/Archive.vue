<template>
  <div class="archive-page container">
    <header class="page-header">
      <h1>归档</h1>
      <p>时光的印记</p>
    </header>
    
    <div class="archive-content">
      <div v-for="(months, year) in archiveData" :key="year" class="year-section">
        <h2 class="year">{{ year }}</h2>
        
        <div v-for="(articles, month) in months" :key="month" class="month-section">
          <h3 class="month">{{ month }} <span>({{ articles.length }})</span></h3>
          <ul class="article-list">
            <li v-for="article in articles" :key="article.id">
              <span class="date">{{ article.date }}</span>
              <router-link :to="`/article/${article.id}`">{{ article.title }}</router-link>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { archiveData } from '@/data/mock'
</script>

<style lang="scss" scoped>
.page-header {
  text-align: center;
  padding: 40px 0;
  margin-bottom: 30px;
  
  h1 {
    color: #2C3E50;
    font-size: 2em;
    margin-bottom: 10px;
  }
  p { color: #95A5A6; }
}

.archive-content {
  max-width: 700px;
  margin: 0 auto;
  background: #fff;
  border-radius: 12px;
  padding: 40px;
  box-shadow: 0 2px 10px rgba(0,0,0,0.05);
}

.year-section {
  margin-bottom: 30px;
  
  .year {
    color: #2C3E50;
    font-size: 1.8em;
    margin-bottom: 20px;
    padding-bottom: 10px;
    border-bottom: 3px solid #1ABC9C;
  }
}

.month-section {
  margin-bottom: 25px;
  margin-left: 20px;
  
  .month {
    color: #555;
    font-size: 1.1em;
    margin-bottom: 12px;
    
    span { color: #95A5A6; font-weight: normal; }
  }
}

.article-list {
  list-style: none;
  margin-left: 15px;
  border-left: 2px solid #ECF0F1;
  padding-left: 20px;
  
  li {
    margin-bottom: 12px;
    display: flex;
    align-items: center;
    gap: 15px;
    
    &::before {
      content: '';
      width: 8px;
      height: 8px;
      background: #1ABC9C;
      border-radius: 50%;
      margin-left: -24px;
    }
    
    .date {
      color: #95A5A6;
      font-size: 13px;
      flex-shrink: 0;
    }
    
    a {
      color: #555;
      &:hover { color: #1ABC9C; }
    }
  }
}
</style>

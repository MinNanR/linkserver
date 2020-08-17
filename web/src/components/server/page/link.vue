<template>
  <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
    <div
      class="panel panel-default col-md-offset-3 col-md-6 col-xs-12"
      v-for="(item, index) in linkList"
      :key="index"
    >
      <div class="panel-heading" role="tab">
        <h4 class="panel-title">
          <a
            role="button"
            :href="`#collapse${item.index}`"
            data-toggle="collapse"
            data-parent="#accordion"
            aria-expanded="true"
            aria-controls="collapseOne"
          >
            <span style="line-height: 2">
              <span style="font-weight: bold">{{item.name}}</span>
              <span
                style="float:right;font-size: 0.5rem"
                th:text="${'更新时间:' +  #dates.format(item.createTime, 'yyyy-MM-dd HH:mm:ss')}"
              >{{item.createTime}}</span>
            </span>
          </a>
        </h4>
      </div>
      <div
        class="panel-collapse collapse"
        role="tabpanel"
        aria-labelledby="headingOne"
        :id="`collapse${item.index}`"
      >
        <div class="panel-body">
          <div class="link-div" style="font-size: 1.5rem">{{item.link}}</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      linkList: [],
    };
  },
  methods: {
    getLinkList() {
      this.request.post("/api/getAllLinkList").then((response) => {
        this.linkList = response
      });
    },
  },
  mounted() {
    this.getLinkList()
  },
};
</script>

<style>
.link-div {
  font-size: 1.25rem;
  overflow: visible;
  text-overflow: inherit;
  white-space: normal;
  word-break: break-all;
}
</style>
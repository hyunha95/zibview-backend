package com.view.zib.domain.post.controller.response;

import com.view.zib.domain.post.entity.ContractInfo;
import com.view.zib.domain.post.entity.Post;
import com.view.zib.domain.storage.service.StorageService;
import lombok.Builder;

import java.time.LocalDateTime;

public record GetPostsResponse(
        Long postId,
        String address,
        String buildingName,
        String imageUrl,
        String imageUrn,
        int likeCount,
        int commentCount,
        DepositRent depositRent,
        MonthlyRent monthlyRent,
        MixedRent mixedRent
) {

    @Builder
    public GetPostsResponse {
    }

    public static GetPostsResponse from(Post post, StorageService storageService) {
        return GetPostsResponse.builder()
                .postId(post.getId())
                .address(post.getAddress().getAddress())
                .buildingName(post.getAddress().getBuildingName())
                .imageUrl(storageService.generateImageUrl(post.getImage()))
                .imageUrn(storageService.generateImageUrn(post.getImage()))
                .likeCount(post.getLikeCount())
                .commentCount(post.getCommentCount())
                .depositRent(DepositRent.from(post.getDepositContractInfo()))
                .monthlyRent(MonthlyRent.from(post.getMonthlyContractInfo()))
                .mixedRent(MixedRent.from(post.getMixedContractInfo()))
                .build();
    }

    // 전제
    public record DepositRent(double deposit, double maintenanceFee, LocalDateTime lastUpdatedAt) {
        public static DepositRent from(ContractInfo contractInfo) {
            return new DepositRent(
                    contractInfo.getDeposit().doubleValue(),
                    contractInfo.getMaintenanceFee().doubleValue(),
                    contractInfo.getUpdatedAt()
            );
        }
    }

    // 월세
    public record MonthlyRent(double deposit, double monthlyFee, double maintenanceFee, LocalDateTime lastUpdatedAt) {
        public static MonthlyRent from(ContractInfo contractInfo) {
            return new MonthlyRent(
                    contractInfo.getDeposit().doubleValue(),
                    contractInfo.getMonthlyFee().doubleValue(),
                    contractInfo.getMaintenanceFee().doubleValue(),
                    contractInfo.getUpdatedAt()
            );
        }
    }

    // 반전세
    public record MixedRent(double deposit, double monthlyFee, double maintenanceFee, LocalDateTime lastUpdatedAt) {
        public static MixedRent from(ContractInfo contractInfo) {
            return new MixedRent(
                    contractInfo.getDeposit().doubleValue(),
                    contractInfo.getMonthlyFee().doubleValue(),
                    contractInfo.getMaintenanceFee().doubleValue(),
                    contractInfo.getUpdatedAt()
            );
        }
    }
}

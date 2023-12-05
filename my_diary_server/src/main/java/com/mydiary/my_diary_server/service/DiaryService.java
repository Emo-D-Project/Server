package com.mydiary.my_diary_server.service;

import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.mydiary.my_diary_server.domain.Comment;
import com.mydiary.my_diary_server.domain.Diary;
import com.mydiary.my_diary_server.domain.Files;
import com.mydiary.my_diary_server.domain.Likes;
import com.mydiary.my_diary_server.domain.Report;
import com.mydiary.my_diary_server.dto.*;
import com.mydiary.my_diary_server.repository.CommentRepository;
import com.mydiary.my_diary_server.repository.DiaryRepository;
import com.mydiary.my_diary_server.repository.FilesRepository;
import com.mydiary.my_diary_server.repository.LikesRepository;
import com.mydiary.my_diary_server.repository.ReportRepository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;


@Slf4j
@RequiredArgsConstructor
@Service
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final LikesRepository likesRepository;
    private final CommentRepository commentsRepository;
    private final ReportRepository reportRepository;
    private final FilesRepository filesRepository;
	private final Storage storage;

	@Value("${spring.cloud.gcp.storage.bucket}") // application.yml에 써둔 bucket 이름
	private String bucketName;
    public Diary save(AddDiaryRequest req, List<MultipartFile> imageFile, MultipartFile audio, String author) throws IOException {
		// 이미지와 오디오 처리하는 부분
		Diary diary = new Diary(Long.parseLong(author), req.getContent(), req.getEmotion(), req.getIs_share(), req.getIs_comm());

		String url = "https://storage.googleapis.com/emod_project/";
		
		//클라우드에 이미지 업로드
		if(!audio.isEmpty()){
			String uuidAudio = UUID.randomUUID().toString();
			
			String ext = audio.getContentType();

			List<String > uuidImages = new ArrayList<String>();
			
			
			BlobInfo blobInfo = storage.create(
					BlobInfo.newBuilder(bucketName, uuidAudio)
							.setContentType(ext)
							.build(),
					audio.getInputStream()
			);

			diary.setAudio(url + uuidAudio);
			
		}

		if(imageFile != null)
		{
			if(!imageFile.get(0).isEmpty()){
				int i;
				for (i=0; i<imageFile.size(); i++) {
					String ext = imageFile.get(i).getContentType();
					String uuid = UUID.randomUUID().toString();

					BlobInfo blobInfo = storage.create(
							BlobInfo.newBuilder(bucketName, uuid)
									.setContentType(ext)
									.build(),
							imageFile.get(i).getInputStream()
					);

					if(i==0) diary.setImage1(url + uuid);
					else if(i==1) diary.setImage2(url +uuid);
					else if(i==2) diary.setImage3(url + uuid);
								}
			}

		}

		return diaryRepository.save(diary);
    }

    public Files view()
    {
    	return filesRepository.findById((long) 1).
    			 orElseThrow(() -> new IllegalArgumentException("not found : " + 1));
    }

    public void upload(String imageByte)
    {
    	filesRepository.save(new Files(imageByte));
    }
    
    public List<Diary> findAll() {
        return diaryRepository.findAll();
    }
    
    public List<Diary> findMine(Long user_id)
    {
    	return diaryRepository.findByUserId(user_id);
    }

    public Diary findById(long id) {
        return diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    
    }

    public Integer getCount(Long user_id)
    {
    	return diaryRepository.findByUserId(user_id).size();
    }
    
    public LocalDateTime getFirst(Long user_id)
    {
    	return diaryRepository.findByUserId(user_id).get(0).getCreatedAt();
    }
    
    public String getMost(Long user_id)
    {
    	int[] counts = {0, 0, 0, 0};
    	List<Diary> list = diaryRepository.findByUserId(user_id);
    	int i;
    	for(i=0; i<list.size(); i++)
    	{
    		int hour = list.get(i).getCreatedAt().getHour();
    		if(hour>=00 && hour<06 )
    		{
    			counts[0]++;
    		}
    		else if(hour>=06 && hour<12)
    		{
    			counts[1]++;
    		}
    		else if(hour>=12 && hour<18)
    		{
    			counts[2]++;
    		}
    		else
    		{
    			counts[3]++;
    		}
    	}
  
    	int max = 0; int maxIndex = 0;
    	for(i=0; i<4; i++)
    	{
    		if(counts[i] > max)
    		{
    			maxIndex = i;
    			max = counts[i];
    		}
    	}
    	
    	switch(maxIndex)
    	{
    	case 0:
    		return "새벽(00~06)";
    	case 1:
    		return "아침(06~12)";
    	case 2:
    		return "오후(12~18)";
    	case 3:
    		return "밤(18~00)";
    	}
		return null;
    }


    public void delete(long id) {
        Diary article = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

       // authorizeArticleAuthor(article);
      //authorize
        diaryRepository.delete(article);
    }

    /*
    public DiaryReportResponse getReport(Long user_id)
    {
    	List<Diary> diary = diaryRepository.findByUserId(user_id);
    	DiaryReportResponse result;
    	int i;
    	for(i=0; i<diary.size(); i++)
    	{
    		result = new DiaryReportResponse(diary.get(i).getCreatedAt());
    	}
    	
    }
    */
      
    public Diary searchPopular(Long user_id)
    {
    	List<Diary> list = diaryRepository.findByUserId(user_id);
    	int i, max=0;
    	Diary popular = new Diary();
    	
    	for(i=0; i<list.size(); i++)
    	{
    		if(list.get(i).getEmpathy() >= max)
    		{
    			popular = list.get(i);
    			max = list.get(i).getEmpathy();
    		}
    			
    	}
    	return popular;
    }
    
    public Integer checkLike(String author, Long id)
    {
    	Likes like = likesRepository.findByUserIdAndPostId(Long.parseLong(author), id);
    	if(like != null)
    		return 1;
    	else
    		return 0;
    }
    
    
    public ReportDTO createReport(String author, String comment)
    {
    	ReportDTO report = new ReportDTO();
    	LocalDateTime now = LocalDateTime.now();
    	
    	report.setDate(YearMonth.from(now));
    	
    	List<Diary> list = findByMonth(now, author);
    	
    	report.setComment(comment);
    	
    	int i;
    	Integer[] emotions = {0, 0, 0, 0, 0, 0, 0};
    	for(i=0; i<list.size(); i++)
    	{
    		if(list.get(i).getEmotion().equals("smile"))
    			emotions[0]++;
    		else if(list.get(i).getEmotion().equals("flutter"))
    			emotions[1]++;
    		else if(list.get(i).getEmotion().equals("angry"))
    			emotions[2]++;
    		else if(list.get(i).getEmotion().equals("annoying"))
    			emotions[3]++;
    		else if(list.get(i).getEmotion().equals("tired"))
    			emotions[4]++;
    		else if(list.get(i).getEmotion().equals("sad"))
    			emotions[5]++;
    		else if(list.get(i).getEmotion().equals("calmness"))
    			emotions[6]++;
    	}
    	
    	int max, maxIndex, min, minIndex;
    	
    	max = emotions[0];
    	maxIndex = emotions[0];
    	min = 2;
    	minIndex = emotions[0];

    	report.setEmotionNums(emotions);
    	
    	for(i=0; i<7; i++)
    	{
    		if(emotions[i] > max)
    		{
    			max = emotions[i];
    			maxIndex = i;
    		}
    		if(emotions[i] < min)
    		{
    			if(emotions[i] != 0)
    			{
        			min = emotions[i];
        			minIndex = i;	
    			}
    		}
    	}
    	
    	report.setLeastEmotion(minIndex);
    	report.setMostEmotion(maxIndex);
    	
    	Integer one = emotions[0] + emotions[1];
    	Integer two = emotions[2] + emotions[3] + emotions[4] + emotions[5];
    	
    	if(one > two)
    	{
    		if(one > emotions[6])
    			report.setScore(1);
    		else
    			report.setScore(3);
    	}
    	else
    	{
    		if(two > emotions[6])
    			report.setScore(2);
    		else
    			report.setScore(3);
    	}
    	reportRepository.save(new Report(Long.parseLong(author), report));
    	return report;
    }
    
    public List<Report> getReports(String author)
    {
    	List<Report> list = reportRepository.findByUserId(Long.parseLong(author));
    	return list;
    }
    
    public Integer getCommentsNums(Diary diary)
    {
    	List<Comment> list = commentsRepository.findByPostId(diary.getId());
    	int i;
    	
    	for(i=0; i<list.size(); i++)
    	{
    		
    	}
    	return i;
    }
    
    public CalendarInfo setCalendar(String author)
    {
    	CalendarInfo cal = new CalendarInfo();
    	List<Diary> calCon = diaryRepository.findByUserId(Long.parseLong(author));
    	
    	cal.setDiaries(calCon);
    	return cal;
    } 

    
    public List<CalendarResponse> getCalendar(Long userId)
    {
    	List<Diary> diaries = diaryRepository.findByUserId(userId);
    	int i;
    	for(i=0; i<diaries.size(); i++)
    	{
    		System.out.println(diaries.get(i).getEmotion());
    	}
		List<CalendarResponse> responses = diaries.stream().map(CalendarResponse::new).toList();	
		return responses;
    }
   
    
    public List<Diary> findByMonth(LocalDateTime date, String author)
    {
    	List<Diary> data = diaryRepository.findByUserId(Long.parseLong(author));
    	List<Diary> result = new ArrayList<Diary>();
    	
    	int i;
    	for(i=0; i<data.size(); i++)
    	{
    		if(data.get(i).getCreatedAt().getMonth() == date.getMonth())
    		{
    			if(data.get(i).getCreatedAt().getYear() == date.getYear())
    			{
    				result.add(data.get(i));
    			}
    		}
    	}
    	return result;
    }

    
    public YearMonth findMostRepeatedValue(ArrayList<YearMonth> list)
    {
    	if(list==null) return null;
    	
    	Map<YearMonth, Integer> frequencyMap = new HashMap<>();
    	
    	for(YearMonth value : list)
    	{
    		frequencyMap.put(value, frequencyMap.getOrDefault(value, 0) + 1);
    	}
    	
    	int maxFrequency = 0;
    	YearMonth mostRepeatedValue = null;
    	
    	Set<Map.Entry<YearMonth, Integer>> entrySet = frequencyMap.entrySet();
    	for(Map.Entry<YearMonth, Integer> entry : entrySet)
    	{
    		if(entry.getValue() > maxFrequency)
    		{
    			maxFrequency = entry.getValue();
    			mostRepeatedValue = entry.getKey();
    		}
    	}
    	
    	return mostRepeatedValue;
    }

    public Double[] getEmotionsAll(String author)
    {
    	Double[] emotions = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
    	List<Diary> list = diaryRepository.findByUserId(Long.parseLong(author));
    	int i;
    	for(i=0; i<list.size(); i++)
    	{
    		if(list.get(i).getEmotion().equals("smile"))
    			emotions[0]++;
    		else if(list.get(i).getEmotion().equals("flutter"))
    			emotions[1]++;
    		else if(list.get(i).getEmotion().equals("angry"))
    			emotions[2]++;
    		else if(list.get(i).getEmotion().equals("annoying"))
    			emotions[3]++;
    		else if(list.get(i).getEmotion().equals("tired"))
    			emotions[4]++;
    		else if(list.get(i).getEmotion().equals("sad"))
    			emotions[5]++;
    		else if(list.get(i).getEmotion().equals("calmness"))
    			emotions[6]++;
    	}
    	return emotions;
    }
    
    public AnalysisResponse getAnalysis(String author)
    {
    	AnalysisResponse result = new AnalysisResponse();
    	YearMonth mostMonth = getMostMonth(Long.parseLong(author));
    	Diary popular = searchPopular(Long.parseLong(author));
    	result.setNums(getCount(Long.parseLong(author)));
    	result.setEmotions(getEmotionsAll(author));
    	result.setMostWritten(getMost(Long.parseLong(author)));
    	result.setFirstDate(getFirst(Long.parseLong(author)));
    	result.setMostYearMonth(LocalDate.of(mostMonth.getYear(), mostMonth.getMonthValue(), 1));
    	result.setMostNums(getMostMonthDiaries(mostMonth, Long.parseLong(author)));
    	result.setMostViewed(popular.getId());
    	result.setMostViewedEmpathy(popular.getEmpathy());
    	result.setMostViewedComments(getCommentsNums(popular));
    	return result;
    }
    
    public YearMonth getMostMonth(Long user_id)
    {
    	ArrayList<YearMonth> ym = new ArrayList<YearMonth>();
    	List<Diary> data = diaryRepository.findByUserId(user_id);
    	int i;
    	for(i=0; i<data.size(); i++)
    	{
    		YearMonth buffer = YearMonth.from(data.get(i).getCreatedAt());
    		ym.add(buffer);
    	}
    	YearMonth result = findMostRepeatedValue(ym);
    	return result;
    }    
    
    public Integer getMostMonthDiaries(YearMonth date, Long user_id)
    {
    	List<Diary> data = diaryRepository.findByUserId(user_id);
    	int i, result = 0;
    	for(i=0; i<data.size(); i++)
    	{
    		if(data.get(i).getCreatedAt().getYear() == date.getYear())
    		{
    			if(data.get(i).getCreatedAt().getMonthValue() == date.getMonthValue())
    				result++;
    		}
    	}
    	return result;
    }
    
    public void recommend(LikesDTO dto)
    {
    	Likes likes = likesRepository.findByUserIdAndPostId(dto.getUserId(), dto.getPostId());
    	Diary article = diaryRepository.findById(dto.getPostId())
    			.orElseThrow(() -> new IllegalArgumentException("not found : " + dto.getPostId()));
    		
    	if(likes == null)
    	{
    		article.recommend(true);
    		likesRepository.save(new Likes(dto.getPostId(), dto.getUserId()));
    	}
    		
    	else
    	{
    		article.recommend(false);
    		likesRepository.delete(likes);
    	}
    }
    
    @Transactional
    public Diary update(long id, UpdateDiaryRequest request) {
        Diary article = diaryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        //authorize
        article.update(request.getEmotion(), request.getContent(), request.getIs_share(), request.getIs_comm());
        
        return article;
    }

  
    // 일기를 작성한 유저인지 확인
    private static void authorizeArticleAuthor(Diary diary) {
    	String author = SecurityContextHolder.getContext().getAuthentication().getName();
    	if(!(diary.getUserId() == Long.parseLong(author)))
    		throw new IllegalArgumentException("not authorized");
        }
   }